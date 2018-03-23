import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ISubscription } from 'rxjs/Subscription';

import { RegularExpressions } from '@shared/constants/regexps/regular-expressions';
import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { Subject } from '@shared/domain/subject/subject';
import { RegisterDetails } from '@shared/domain/register/register-details';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.scss'],
  providers: [SubjectDetailsService],
})
export class AddEmployeeComponent implements OnInit, OnDestroy {
  private $newSubject: ISubscription;
  public subject: Subject;
  public registerDetails: RegisterDetails;

  public personalInformationFormGroup: FormGroup = new FormGroup({
    firstNameFormControl: new FormControl('', [Validators.required]),

    lastNameFormControl: new FormControl('', [Validators.required]),

    middleNameFormControl: new FormControl('', []),

    dobFormControl: new FormControl('', [Validators.required]),

    positionFormControl: new FormControl({ disabled: true }, []),
  });

  public contactInformationFormGroup: FormGroup = new FormGroup({
    emailFormControl: new FormControl('', [Validators.required, Validators.pattern(RegularExpressions.EMAIL)]),

    telephoneFormControl: new FormControl('', [
      Validators.required,
      Validators.pattern(RegularExpressions.NUMBERS_ONLY),
      Validators.minLength(7),
      Validators.maxLength(11),
    ]),

    firstLineAddressFormControl: new FormControl('', []),

    secondLineAddressFormControl: new FormControl('', []),

    thirdLineAddressFormControl: new FormControl('', []),

    postcodeFormControl: new FormControl('', [Validators.required, Validators.pattern(RegularExpressions.UK_POSTCODE)]),

    cityFormControl: new FormControl('', []),

    countryFormControl: new FormControl('', []),
  });

  public employeeDetailsFormGroup: FormGroup = new FormGroup({
    ninFormControl: new FormControl('', [Validators.required, Validators.pattern(RegularExpressions.NIN)]),

    startDateFormControl: new FormControl('', []),

    endDateFormControl: new FormControl('', []),

    selfAssignFormControl: new FormControl([]),
  });

  public loginInformationFormGroup: FormGroup = new FormGroup({
    passwordFormControl: new FormControl('', [Validators.required]),

    repeatPasswordFormControl: new FormControl('', [Validators.required]),

    usernameFormControl: new FormControl('', [Validators.required]),
  });

  public stepNumber = 0;

  constructor(private _subjectDetailsService: SubjectDetailsService) {}

  ngOnInit(): void {}

  ngOnDestroy(): void {}

  public setStep(stepNumber: number): void {
    this.stepNumber = stepNumber;
  }

  public getStep(): number {
    return this.stepNumber;
  }

  public nextStep(): void {
    this.stepNumber++;
  }

  public prevStep(): void {
    this.stepNumber--;
  }

  public isValid(): boolean {
    return this.personalInformationFormGroup.valid && this.contactInformationFormGroup.valid && this.employeeDetailsFormGroup.valid;
  }

  public arePasswordsIdentical(password1: string, password2: string): boolean {
    if (password1 !== password2) {
      this.loginInformationFormGroup.controls['repeatPasswordFormControl'].setErrors({ passwordDoNotMatch: true });
      return false;
    }
    return true;
  }

  public submitForm(subject: Subject, selfAssign: boolean): void {
    if (this.isValid()) {
      this.createSubject(subject);
      if (selfAssign) {
        this.assignEmployeeToManager();
      }
    }
  }

  public createSubject(subject: Subject): void {
    this.$newSubject = this._subjectDetailsService.createSubject(subject).subscribe();
  }

  public assignEmployeeToManager(): void {
    /**
     * call a service
     */
  }
}
