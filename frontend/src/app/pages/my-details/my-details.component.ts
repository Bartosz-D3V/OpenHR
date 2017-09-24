import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { RegularExpressions } from '../../shared/constants/regular-expressions';
import { Subject } from '../../shared/classes/subject/subject';
import { SubjectService } from '../../shared/services/subject/subject.service';

@Component({
  selector: 'app-my-details',
  templateUrl: './my-details.component.html',
  styleUrls: ['./my-details.component.scss'],
  providers: [SubjectService],
})
export class MyDetailsComponent implements OnInit {

  public subject: Subject;

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

  constructor(private subjectService: SubjectService) {
  }

  ngOnInit(): void {
    this.subject = this.subjectService.getCurrentSubject();
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

}
