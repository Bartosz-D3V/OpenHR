import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

import { RegularExpressions } from '../../shared/constants/regular-expressions';
import { Subject } from './classes/subject';
import { MyDetailsService } from './service/my-details.service';
import { ErrorResolverService } from '../../shared/services/error-resolver/error-resolver.service';

@Component({
  selector: 'app-my-details',
  templateUrl: './my-details.component.html',
  styleUrls: ['./my-details.component.scss'],
  providers: [MyDetailsService, ErrorResolverService],
})
export class MyDetailsComponent implements OnInit {

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

  constructor(private _myDetailsService: MyDetailsService) {
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

  getCurrentSubject() {
    this._myDetailsService
      .getCurrentSubject()
      .subscribe((subject: Subject) => {
        this.subject = subject;
      });
  }

}
