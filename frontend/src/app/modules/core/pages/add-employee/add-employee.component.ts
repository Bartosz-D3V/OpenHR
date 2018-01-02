import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISubscription } from 'rxjs/Subscription';

import { RegularExpressions } from '../../../../shared/constants/regexps/regular-expressions';
import { SubjectDetailsService } from '../../../../shared/services/subject/subject-details.service';
import { ConfigService } from '../../../../shared/services/config/config.service';
import { Subject } from '../../../../shared/domain/subject/subject';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.scss'],
  providers: [
    ConfigService,
    SubjectDetailsService,
  ],
})
export class AddEmployeeComponent implements OnInit, OnDestroy {

  private $newSubject: ISubscription;
  public subject: Subject;

  public personalInformationFormGroup: FormGroup = new FormGroup({
    firstNameFormControl: new FormControl('', [
      Validators.required,
    ]),

    lastNameFormControl: new FormControl('', [
      Validators.required,
    ]),

    middleNameFormControl: new FormControl('', []),

    dobFormControl: new FormControl('', [
      Validators.required,
    ]),

    positionFormControl: new FormControl({disabled: true}, []),
  });

  public contactInformationFormGroup: FormGroup = new FormGroup({
    emailFormControl: new FormControl('', [
      Validators.required,
      Validators.pattern(RegularExpressions.EMAIL),
    ]),

    telephoneFormControl: new FormControl('', [
      Validators.required,
      Validators.pattern(RegularExpressions.NUMBERS_ONLY),
      Validators.minLength(7),
      Validators.maxLength(11),
    ]),

    firstLineAddressFormControl: new FormControl('', []),

    secondLineAddressFormControl: new FormControl('', []),

    thirdLineAddressFormControl: new FormControl('', []),

    postcodeFormControl: new FormControl('', [
      Validators.required,
      Validators.pattern(RegularExpressions.UK_POSTCODE),
    ]),

    cityFormControl: new FormControl('', []),

    countryFormControl: new FormControl('', []),
  });

  public employeeDetailsFormGroup: FormGroup = new FormGroup({
    ninFormControl: new FormControl('', [
      Validators.required,
      Validators.pattern(RegularExpressions.NIN),
    ]),

    startDateFormControl: new FormControl('', []),

    endDateFormControl: new FormControl('', []),

    selfAssignFormControl: new FormControl([]),
  });

  public stepNumber = 0;

  constructor() {
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
  }

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
    return this.personalInformationFormGroup.valid &&
      this.contactInformationFormGroup.valid &&
      this.employeeDetailsFormGroup.valid;
  }

}
