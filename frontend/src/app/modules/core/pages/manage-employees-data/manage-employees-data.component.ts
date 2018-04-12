import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs/Observable';
import { ISubscription } from 'rxjs/Subscription';
import { map, startWith } from 'rxjs/operators';
import 'rxjs/add/observable/zip';
import { MatAutocompleteSelectedEvent } from '@angular/material';

import { RegularExpressions } from '@shared/constants/regexps/regular-expressions';
import { ResponsiveHelperService } from '@shared/services/responsive-helper/responsive-helper.service';
import { NotificationService } from '@shared/services/notification/notification.service';
import { EmployeeService } from '@shared/services/employee/employee.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { ManagerService } from '@shared/services/manager/manager.service';
import { Subject } from '@shared/domain/subject/subject';
import { Employee } from '@shared/domain/subject/employee';
import { PersonalInformation } from '@shared/domain/subject/personal-information';
import { ContactInformation } from '@shared/domain/subject/contact-information';
import { EmployeeInformation } from '@shared/domain/subject/employee-information';
import { HrInformation } from '@shared/domain/subject/hr-information';
import { Manager } from '@shared/domain/subject/manager';
import { ManageEmployeesDataService } from './service/manage-employees-data.service';
import { SubjectDetailsService } from '@shared/services/subject/subject-details.service';
import { Role } from '@shared/domain/subject/role';

@Component({
  selector: 'app-manage-employees-data',
  templateUrl: './manage-employees-data.component.html',
  styleUrls: ['./manage-employees-data.component.scss'],
  providers: [ManageEmployeesDataService, EmployeeService, SubjectDetailsService, ResponsiveHelperService],
})
export class ManageEmployeesDataComponent implements OnInit, OnDestroy {
  private $employee: ISubscription;
  private $subjects: ISubscription;
  private $supervisors: ISubscription;
  public isLoadingResults: boolean;
  public stepNumber = 0;
  public subjects: Array<Subject>;
  public supervisors: Array<Subject>;
  public subject: Subject;
  public filteredSubjects: Observable<Array<Subject>>;
  public filteredSupervisors: Observable<Array<Subject>>;
  public employeeForm: FormGroup;
  public subjectsCtrl: FormControl = new FormControl();
  public supervisorsCtrl: FormControl = new FormControl();

  constructor(
    private _manageEmployeesDataService: ManageEmployeesDataService,
    private _employeeService: EmployeeService,
    private _subjectDetailsService: SubjectDetailsService,
    private _responsiveHelper: ResponsiveHelperService,
    private _notificationService: NotificationService,
    private _errorResolver: ErrorResolverService,
    private _fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.fetchSubjects();
  }

  ngOnDestroy(): void {
    this.unsubscribeAll();
  }

  public setStep(stepNumber: number): void {
    this.stepNumber = stepNumber;
  }

  public nextStep(): void {
    this.stepNumber++;
  }

  public prevStep(): void {
    this.stepNumber--;
  }

  public constructForm(): void {
    const perInfo: PersonalInformation = this.subject.personalInformation;
    const contactInfo: ContactInformation = this.subject.contactInformation;
    const employeeInfo: EmployeeInformation = this.subject.employeeInformation;
    const hrInfo: HrInformation = this.subject.hrInformation;

    this.employeeForm = this._fb.group({
      subjectId: [this.subject.subjectId],
      personalInformation: this._fb.group({
        firstName: [perInfo.firstName, Validators.required],
        middleName: [perInfo.middleName],
        lastName: [perInfo.lastName, Validators.required],
        dateOfBirth: [perInfo.dateOfBirth, Validators.required],
      }),
      contactInformation: this._fb.group({
        telephone: [
          contactInfo.telephone,
          Validators.compose([
            Validators.required,
            Validators.pattern(RegularExpressions.NUMBERS_ONLY),
            Validators.minLength(7),
            Validators.maxLength(11),
          ]),
        ],
        email: [contactInfo.email, Validators.compose([Validators.required, Validators.pattern(RegularExpressions.EMAIL)])],
        address: this._fb.group({
          firstLineAddress: [contactInfo.address.firstLineAddress],
          secondLineAddress: [contactInfo.address.secondLineAddress],
          thirdLineAddress: [contactInfo.address.thirdLineAddress],
          postcode: [
            contactInfo.address.postcode,
            Validators.compose([Validators.required, Validators.pattern(RegularExpressions.UK_POSTCODE)]),
          ],
          city: [contactInfo.address.city],
          country: [contactInfo.address.country],
        }),
      }),
      employeeInformation: this._fb.group({
        nationalInsuranceNumber: [
          employeeInfo.nationalInsuranceNumber,
          Validators.compose([Validators.required, Validators.pattern(RegularExpressions.NIN)]),
        ],
        position: [employeeInfo.position],
        department: [employeeInfo.department],
        employeeNumber: [employeeInfo.employeeNumber, Validators.required],
        startDate: [employeeInfo.startDate],
        endDate: [employeeInfo.endDate],
      }),
      hrInformation: this._fb.group({
        allowance: [hrInfo.allowance, Validators.min(0)],
        usedAllowance: [hrInfo.usedAllowance, Validators.min(0)],
      }),
      role: [this.subject.role],
    });

    this.supervisorsCtrl.setValidators(Validators.required);
    this.supervisorsCtrl.setValue(this.getSubjectSupervisor(this.subject));
  }

  public reduceSubjects(subjects: Array<Employee>): Observable<Array<Subject>> {
    return this.subjectsCtrl.valueChanges.pipe(
      startWith(''),
      map(lastName => (typeof lastName === 'string' ? lastName : '')),
      map(subject => (subject ? this.filterSubjects(subjects, subject) : subjects.slice()))
    );
  }

  public reduceSupervisors(subjects: Array<Subject>): Observable<Array<Subject>> {
    return this.supervisorsCtrl.valueChanges.pipe(
      startWith(''),
      map(lastName => (typeof lastName === 'string' ? lastName : '')),
      map(subject => (subject ? this.filterSubjects(subjects, subject) : subjects.slice()))
    );
  }

  public filterSubjects(subjects: Array<Subject>, lastName: string): Array<Subject> {
    return subjects.filter(subject => subject.personalInformation.lastName.toLowerCase().indexOf(lastName.toLowerCase()) === 0);
  }

  public fetchSubjects(): void {
    this.$subjects = this._subjectDetailsService.getSubjects().subscribe(
      (response: Array<Subject>) => {
        this.subjects = response;
        this.filteredSubjects = this.reduceSubjects(response);
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public fetchSelectedSubject(subjectId: number): void {
    this.isLoadingResults = true;
    this.$employee = this._subjectDetailsService.getSubjectById(subjectId).subscribe(
      (response: Subject) => {
        this.subject = response;
        this.constructForm();
        this.isLoadingResults = false;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public fetchSupervisors(): void {
    this.$supervisors = this._manageEmployeesDataService.getSupervisors().subscribe(
      (response: Array<Manager>) => {
        this.supervisors = response;
        this.filteredSupervisors = this.reduceSupervisors(response);
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public displayFullName(subject?: Subject): string | undefined {
    return subject && subject.personalInformation
      ? `${subject.personalInformation.firstName} ${subject.personalInformation.lastName}`
      : undefined;
  }

  public displaySubject($event: MatAutocompleteSelectedEvent): void {
    const subjectId: number = (<Employee>$event.option.value).subjectId;
    this.fetchSelectedSubject(subjectId);
    this.fetchSupervisors();
  }

  public save(): void {
    const updatedSubject: Subject = <Subject>this.employeeForm.value;
    const updatedSupervisor: Subject = <Subject>this.supervisorsCtrl.value;
    Observable.zip(
      this._subjectDetailsService.updateSubject(updatedSubject),
      this._manageEmployeesDataService.updateSubjectsSupervisor(updatedSubject.subjectId, updatedSupervisor.subjectId),
      (subject: Subject) => ({ subject })
    ).subscribe(
      pair => {
        const msg = `Employee with id ${pair.subject.subjectId} has been updated`;
        this._notificationService.openSnackBar(msg, 'OK');
        this.subject = pair.subject;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public getSubjectSupervisor(subject: Subject): Subject {
    return subject.role === Role.EMPLOYEE ? subject['manager'] : this.subject.role === Role.MANAGER ? subject['hrTeamMember'] : null;
  }

  public isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }

  public isValid(): boolean {
    return this.employeeForm.valid && this.supervisorsCtrl.valid;
  }

  private unsubscribeAll(): void {
    if (this.$employee !== undefined) {
      this.$employee.unsubscribe();
    }
    if (this.$supervisors !== undefined) {
      this.$supervisors.unsubscribe();
    }
  }
}
