import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { ISubscription } from 'rxjs/Subscription';
import 'rxjs/add/operator/finally';
import 'rxjs/add/operator/retry';

import { NAMED_DATE } from '@config/datepicker-format';
import { RegularExpressions } from '@shared/constants/regexps/regular-expressions';
import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { ResponsiveHelperService } from '@shared/services/responsive-helper/responsive-helper.service';
import { NotificationService } from '@shared/services/notification/notification.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { Subject } from '@shared/domain/subject/subject';
import { PersonalInformation } from '@shared/domain/subject/personal-information';
import { ContactInformation } from '@shared/domain/subject/contact-information';
import { HrInformation } from '@shared/domain/subject/hr-information';
import { EmployeeInformation } from '@shared/domain/subject/employee-information';
import { CustomAsyncValidatorsService } from '@shared/util/async-validators/custom-async-validators.service';
import { SystemVariables } from '@config/system-variables';
import { PersonalDetailsService } from './service/personal-details.service';

@Component({
  selector: 'app-personal-details',
  templateUrl: './personal-details.component.html',
  styleUrls: ['./personal-details.component.scss'],
  providers: [
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
    { provide: MAT_DATE_FORMATS, useValue: NAMED_DATE },
    SubjectDetailsService,
    NotificationService,
    ResponsiveHelperService,
    PersonalDetailsService,
  ],
})
export class PersonalDetailsComponent implements OnInit, OnDestroy {
  private $currentSubject: ISubscription;
  public isLoading: boolean;
  public isFetching: boolean;
  public personalDetailsFormGroup: FormGroup;
  public stepNumber = 0;
  public subject: Subject;

  constructor(
    private _subjectDetailsService: SubjectDetailsService,
    private _personalDetailsService: PersonalDetailsService,
    private _responsiveHelper: ResponsiveHelperService,
    private _errorResolver: ErrorResolverService,
    private _notificationService: NotificationService,
    private _asyncValidator: CustomAsyncValidatorsService,
    private _fb: FormBuilder
  ) {}

  public ngOnInit(): void {
    this.getCurrentSubject();
  }

  public ngOnDestroy(): void {
    this.$currentSubject.unsubscribe();
  }

  public buildForm(): void {
    const perInfo: PersonalInformation = this.subject.personalInformation;
    const contactInfo: ContactInformation = this.subject.contactInformation;
    const employeeInfo: EmployeeInformation = this.subject.employeeInformation;
    const hrInfo: HrInformation = this.subject.hrInformation;

    this.personalDetailsFormGroup = this._fb.group({
      subjectId: [this.subject.subjectId || ''],
      personalInformation: this._fb.group({
        firstName: [perInfo.firstName || '', Validators.required],
        middleName: [perInfo.middleName || ''],
        lastName: [perInfo.lastName || '', Validators.required],
        dateOfBirth: [perInfo.dateOfBirth || '', Validators.required],
      }),
      contactInformation: this._fb.group({
        telephone: [
          contactInfo.telephone || '',
          Validators.compose([
            Validators.required,
            Validators.pattern(RegularExpressions.NUMBERS_ONLY),
            Validators.minLength(7),
            Validators.maxLength(11),
          ]),
        ],
        email: [
          contactInfo.email || '',
          Validators.compose([Validators.required, Validators.pattern(RegularExpressions.EMAIL)]),
          this._asyncValidator.validateEmailIsFree(contactInfo.email),
        ],
        address: this._fb.group({
          firstLineAddress: [contactInfo.address.firstLineAddress || ''],
          secondLineAddress: [contactInfo.address.secondLineAddress || ''],
          thirdLineAddress: [contactInfo.address.thirdLineAddress || ''],
          postcode: [
            contactInfo.address.postcode || '',
            Validators.compose([Validators.required, Validators.pattern(RegularExpressions.UK_POSTCODE)]),
          ],
          city: [contactInfo.address.city || ''],
          country: [contactInfo.address.country || ''],
        }),
      }),
      employeeInformation: this._fb.group({
        nationalInsuranceNumber: [
          employeeInfo.nationalInsuranceNumber || '',
          Validators.compose([Validators.required, Validators.pattern(RegularExpressions.NIN)]),
        ],
        position: [employeeInfo.position || '', Validators.required],
        department: [employeeInfo.department || ''],
        employeeNumber: [employeeInfo.employeeNumber || '', Validators.required],
        startDate: [employeeInfo.startDate || ''],
        endDate: [employeeInfo.endDate || ''],
      }),
      hrInformation: this._fb.group({
        allowance: [hrInfo.allowance || '', Validators.compose([Validators.min(0), Validators.required])],
        usedAllowance: [hrInfo.usedAllowance || '', Validators.compose([Validators.min(0), Validators.required])],
      }),
      role: [this.subject.role || '', Validators.required],
    });

    this.personalDetailsFormGroup.get('role').disable();
    this.personalDetailsFormGroup.get('hrInformation').disable();
  }

  public getCurrentSubject(): void {
    this.isFetching = true;
    this.$currentSubject = this._subjectDetailsService
      .getCurrentSubject()
      .retry(SystemVariables.RETRY_TIMES)
      .finally(() => (this.isFetching = false))
      .subscribe(
        (response: Subject) => {
          this.subject = response;
          this.buildForm();
        },
        (httpErrorResponse: HttpErrorResponse) => {
          this._errorResolver.handleError(httpErrorResponse.error);
        }
      );
  }

  public save(): void {
    this.isLoading = true;
    const subject: Subject = <Subject>this.personalDetailsFormGroup.getRawValue();
    this._personalDetailsService
      .saveSubject(subject)
      .retry(SystemVariables.RETRY_TIMES)
      .finally(() => (this.isLoading = false))
      .subscribe(
        (result: Subject) => {
          const msg = `Details of user with id ${result.subjectId} have been updated`;
          this._notificationService.openSnackBar(msg, 'OK');
        },
        (httpErrorResponse: HttpErrorResponse) => {
          this._errorResolver.handleError(httpErrorResponse.error);
        }
      );
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
    return this.personalDetailsFormGroup.valid;
  }

  public isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }
}
