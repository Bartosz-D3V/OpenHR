import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material';
import { MomentDateAdapter } from '@angular/material-moment-adapter';

import { ISubscription } from 'rxjs/Subscription';

import { NAMED_DATE } from '../../../../config/datepicker-format';
import { ConfigService } from '../../../../shared/services/config/config.service';
import { RegularExpressions } from '../../../../shared/constants/regexps/regular-expressions';
import { SubjectDetailsService } from '../../../../shared/services/subject/subject-details.service';
import { ResponsiveHelperService } from '../../../../shared/services/responsive-helper/responsive-helper.service';
import { Subject } from '../../../../shared/domain/subject/subject';

@Component({
  selector: 'app-personal-details',
  templateUrl: './personal-details.component.html',
  styleUrls: ['./personal-details.component.scss'],
  providers: [
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: NAMED_DATE},
    SubjectDetailsService,
    ConfigService,
    ResponsiveHelperService,
  ],
})
export class PersonalDetailsComponent implements OnInit, OnDestroy {

  private $currentSubject: ISubscription;

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

    employeeIdFormControl: new FormControl('', [
      Validators.required,
    ]),

    startDateFormControl: new FormControl('', []),

    endDateFormControl: new FormControl('', []),
  });

  public stepNumber = 0;
  public subject: Subject;

  constructor(private _subjectDetailsService: SubjectDetailsService,
              private _configService: ConfigService,
              private _responsiveHelper: ResponsiveHelperService) {
  }

  ngOnInit(): void {
    this.getCurrentSubject();
  }

  ngOnDestroy(): void {
    this.$currentSubject.unsubscribe();
  }

  save(): void {
    if (this.isValid()) {
      this._subjectDetailsService
        .saveSubject(this.subject)
        .subscribe();
    }
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

  getCurrentSubject(): void {
    this.$currentSubject = this._subjectDetailsService
      .getCurrentSubject()
      .subscribe((response: Subject) => {
        this.subject = response;
      });
  }

  isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }
}
