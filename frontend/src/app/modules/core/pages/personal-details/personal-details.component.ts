import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

import { Subject } from '../../../../shared/domain/subject/subject';

import { PersonalDetailsService } from './service/personal-details.service';
import { RegularExpressions } from '../../../../shared/constants/regular-expressions';

@Component({
  selector: 'app-personal-details',
  templateUrl: './personal-details.component.html',
  styleUrls: ['./personal-details.component.scss'],
  providers: [PersonalDetailsService],
})
export class PersonalDetailsComponent implements OnInit {

  firstNameFormControl: FormControl = new FormControl('', [
    Validators.required,
  ]);

  lastNameFormControl: FormControl = new FormControl('', [
    Validators.required,
  ]);

  dobFormControl: FormControl = new FormControl('', [
    Validators.required,
  ]);

  postcodeFormControl: FormControl = new FormControl('', [
    Validators.required,
    Validators.pattern(RegularExpressions.UK_POSTCODE),
  ]);

  emailFormControl: FormControl = new FormControl('', [
    Validators.required,
    Validators.pattern(RegularExpressions.EMAIL),
  ]);

  telephoneFormControl: FormControl = new FormControl('', [
    Validators.required,
    Validators.pattern(RegularExpressions.NUMBERS_ONLY),
    Validators.minLength(7),
    Validators.maxLength(11),
  ]);

  public step = 0;
  public subject: Subject;

  constructor(private _personalDetailsService: PersonalDetailsService) {
  }

  ngOnInit(): void {
    this.getCurrentSubject();
  }

  public setStep(number: number): void {
    this.step = number;
  }

  public getStep(): number {
    return this.step;
  }

  public nextStep(): void {
    this.step++;
  }

  public prevStep(): void {
    this.step--;
  }

  public isValid(): boolean {
    return this.firstNameFormControl.valid &&
      this.lastNameFormControl.valid &&
      this.dobFormControl.valid &&
      this.postcodeFormControl.valid &&
      this.emailFormControl.valid &&
      this.telephoneFormControl.valid;
  }

  getCurrentSubject(): void {
    this._personalDetailsService
      .getCurrentSubject()
      .subscribe((response: Subject) => {
        this.subject = response;
      });
  }

}
