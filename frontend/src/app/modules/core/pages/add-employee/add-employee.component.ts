import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ISubscription } from 'rxjs/Subscription';

import { RegularExpressions } from '@shared/constants/regexps/regular-expressions';
import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { Subject } from '@shared/domain/subject/subject';
import { EmployeeService } from '@shared/services/employee/employee.service';
import { ManagerService } from '@shared/services/manager/manager.service';
import { HrTeamMemberService } from '@shared/services/hr/hr-team-member.service';
import { Role } from '@shared/domain/subject/role';
import { NotificationService } from '@shared/services/notification/notification.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.scss'],
  providers: [SubjectDetailsService],
})
export class AddEmployeeComponent implements OnInit, OnDestroy {
  private $newSubject: ISubscription;
  public newSubjectForm: FormGroup;
  public subject: Subject;
  public stepNumber = 0;

  constructor(
    private _employeeService: EmployeeService,
    private _managerService: ManagerService,
    private _hrService: HrTeamMemberService,
    private _notificationService: NotificationService,
    private _errorResolver: ErrorResolverService,
    private _fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.buildForm();
  }

  ngOnDestroy(): void {}

  private getServiceMethod(subject: Subject): Observable<Subject> {
    switch (subject.role) {
      case Role.EMPLOYEE:
        return this._employeeService.createEmployee(subject);
      case Role.MANAGER:
        return this._managerService.createManager(subject);
      case Role.HRTEAMMEMBER:
        return this._hrService.createHrTeamMember(subject);
    }
  }

  public buildForm(): void {
    this.newSubjectForm = this._fb.group({
      personalInformation: this._fb.group({
        firstName: [Validators.required],
        middleName: [],
        lastName: [Validators.required],
        dateOfBirth: [Validators.required],
      }),
      contactInformation: this._fb.group({
        telephone: [
          Validators.compose([
            Validators.required,
            Validators.pattern(RegularExpressions.NUMBERS_ONLY),
            Validators.minLength(7),
            Validators.maxLength(11),
          ]),
        ],
        email: [Validators.compose([Validators.required, Validators.pattern(RegularExpressions.EMAIL)])],
        address: this._fb.group({
          firstLineAddress: [],
          secondLineAddress: [],
          thirdLineAddress: [],
          postcode: [Validators.compose([Validators.required, Validators.pattern(RegularExpressions.UK_POSTCODE)])],
          city: [],
          country: [],
        }),
      }),
      employeeInformation: this._fb.group({
        nationalInsuranceNumber: [Validators.compose([Validators.required, Validators.pattern(RegularExpressions.NIN)])],
        position: [],
        department: [],
        employeeNumber: [Validators.required],
        startDate: [],
        endDate: [],
      }),
      hrInformation: this._fb.group({
        allowance: [Validators.min(0)],
        usedAllowance: [Validators.min(0)],
      }),
      role: [],
      user: this._fb.group({
        password: [Validators.required],
        username: [Validators.required],
      }),
    });
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
    return this.newSubjectForm.valid;
  }

  public arePasswordsIdentical(password1: string, password2: string): boolean {
    if (password1 !== password2) {
      this.newSubjectForm.get(['user', 'password']).setErrors({ passwordDoNotMatch: true });
      return false;
    }
    return true;
  }

  public save(selfAssign: boolean): void {
    if (this.isValid()) {
      const subject: Subject = <Subject>this.newSubjectForm.value;
      this.createSubject(subject);
      if (selfAssign) {
        this.assignEmployeeToManager();
      }
    }
  }

  public createSubject(subject: Subject): void {
    this.$newSubject = this.getServiceMethod(subject).subscribe(
      (response: Subject) => {
        const message = `Person with id ${response.subjectId} has been created`;
        this._notificationService.openSnackBar(message, 'OK');
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public assignEmployeeToManager(): void {
    /**
     * call a service
     */
  }
}
