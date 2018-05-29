import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { NAMED_DATE } from '@config/datepicker-format';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material';
import { ISubscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/finally';
import 'rxjs/add/operator/retry';

import { SystemVariables } from '@config/system-variables';
import { RegularExpressions } from '@shared/constants/regexps/regular-expressions';
import { Subject } from '@shared/domain/subject/subject';
import { Role } from '@shared/domain/subject/role';
import { EmployeeService } from '@shared/services/employee/employee.service';
import { ManagerService } from '@shared/services/manager/manager.service';
import { HrTeamMemberService } from '@shared/services/hr/hr-team-member.service';
import { NotificationService } from '@shared/services/notification/notification.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { ResponsiveHelperService } from '@shared/services/responsive-helper/responsive-helper.service';
import { CustomValidators } from '@shared/util/validators/custom-validators';
import { ObjectHelper } from '@shared/util/helpers/object-helper';
import { CustomAsyncValidatorsService } from '@shared/util/async-validators/custom-async-validators.service';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.scss'],
  providers: [
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
    { provide: MAT_DATE_FORMATS, useValue: NAMED_DATE },
    EmployeeService,
    ManagerService,
    HrTeamMemberService,
    NotificationService,
    ResponsiveHelperService,
  ],
})
export class AddEmployeeComponent implements OnInit, OnDestroy {
  private $newSubject: ISubscription;
  public isLoading: boolean;
  public roles: Array<Role> = [Role.EMPLOYEE, Role.MANAGER, Role.HRTEAMMEMBER];
  public newSubjectForm: FormGroup;
  public subject: Subject;
  public stepNumber = 0;

  constructor(
    private _employeeService: EmployeeService,
    private _managerService: ManagerService,
    private _hrService: HrTeamMemberService,
    private _notificationService: NotificationService,
    private _asyncValidator: CustomAsyncValidatorsService,
    private _errorResolver: ErrorResolverService,
    private _responsiveHelper: ResponsiveHelperService,
    private _fb: FormBuilder
  ) {}

  public ngOnInit(): void {
    this.buildForm();
  }

  public ngOnDestroy(): void {
    if (this.$newSubject) {
      this.$newSubject.unsubscribe();
    }
  }

  public buildForm(): void {
    this.newSubjectForm = this._fb.group({
      personalInformation: this._fb.group({
        firstName: ['', Validators.required],
        middleName: [''],
        lastName: ['', Validators.required],
        dateOfBirth: ['', Validators.required],
      }),
      contactInformation: this._fb.group({
        telephone: [
          '',
          Validators.compose([
            Validators.required,
            Validators.pattern(RegularExpressions.NUMBERS_ONLY),
            Validators.minLength(7),
            Validators.maxLength(11),
          ]),
        ],
        email: [
          '',
          Validators.compose([Validators.required, Validators.pattern(RegularExpressions.EMAIL)]),
          this._asyncValidator.validateEmailIsFree(),
        ],
        address: this._fb.group({
          firstLineAddress: [''],
          secondLineAddress: [''],
          thirdLineAddress: [''],
          postcode: ['', Validators.compose([Validators.required, Validators.pattern(RegularExpressions.UK_POSTCODE)])],
          city: [''],
          country: [''],
        }),
      }),
      employeeInformation: this._fb.group({
        nationalInsuranceNumber: ['', Validators.compose([Validators.required, Validators.pattern(RegularExpressions.NIN)])],
        position: ['', Validators.required],
        department: [''],
        employeeNumber: ['', Validators.required],
        startDate: [''],
        endDate: [''],
      }),
      hrInformation: this._fb.group({
        allowance: ['', Validators.compose([Validators.required, Validators.min(0), Validators.pattern(RegularExpressions.NUMBERS_ONLY)])],
        usedAllowance: [
          0,
          Validators.compose([Validators.required, Validators.min(0), Validators.pattern(RegularExpressions.NUMBERS_ONLY)]),
        ],
      }),
      role: ['', Validators.required],
      user: this._fb.group(
        {
          password: ['', Validators.required],
          repeatPassword: ['', Validators.required],
          username: ['', Validators.required, this._asyncValidator.validateUsernameIsFree()],
        },
        { validator: CustomValidators.validatePasswords }
      ),
    });
  }

  public createSubject(subject: Subject): void {
    this.isLoading = true;
    this.$newSubject = this.getServiceMethod(subject)
      .finally(() => (this.isLoading = false))
      .retry(SystemVariables.RETRY_TIMES)
      .subscribe(
        (response: Subject) => {
          const message = `Person with id ${response.subjectId} has been created`;
          this._notificationService.openSnackBar(message, 'OK');
        },
        (httpErrorResponse: HttpErrorResponse) => {
          this._errorResolver.handleError(httpErrorResponse.error);
        }
      );
  }

  public getServiceMethod(subject: Subject): Observable<Subject> {
    switch (subject.role) {
      case Role.EMPLOYEE:
        subject = ObjectHelper.removeSubjectHelperProperties(subject);
        return this._employeeService.createEmployee(subject);
      case Role.MANAGER:
        subject = ObjectHelper.removeSubjectHelperProperties(subject);
        return this._managerService.createManager(subject);
      case Role.HRTEAMMEMBER:
        subject = ObjectHelper.removeSubjectHelperProperties(subject);
        return this._hrService.createHrTeamMember(subject);
    }
  }

  public save(): void {
    if (this.isValid()) {
      const subject: Subject = <Subject>this.newSubjectForm.value;
      this.createSubject(subject);
      this.resetForm();
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

  public resetForm(): void {
    this.newSubjectForm.reset({
      hrInformation: {
        usedAllowance: 0,
      },
    });
    this.newSubjectForm.markAsPristine();
    this.newSubjectForm.markAsUntouched();
  }

  public isValid(): boolean {
    return this.newSubjectForm.valid;
  }

  public isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }
}
