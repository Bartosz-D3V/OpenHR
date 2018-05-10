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
import { HrTeamMemberService } from '@shared/services/hr/hr-team-member.service';
import { HrTeamMember } from '@shared/domain/subject/hr-team-member';
import { Role } from '@shared/domain/subject/role';

@Component({
  selector: 'app-manage-workers-data',
  templateUrl: './manage-workers-data.component.html',
  styleUrls: ['./manage-workers-data.component.scss'],
  providers: [EmployeeService, ManagerService, HrTeamMemberService, ResponsiveHelperService],
})
export class ManageWorkersDataComponent implements OnInit, OnDestroy {
  private $workers: ISubscription;
  private $employees: ISubscription;
  private $managers: ISubscription;
  private $hrTeamMembers: ISubscription;
  public isLoadingResults: boolean;
  public stepNumber = 0;
  public employees: Array<Employee>;
  public managers: Array<Manager>;
  public hrTeamMembers: Array<HrTeamMember>;
  public subject: Subject;
  public filteredWorkers: Observable<Array<Subject>>;
  public filteredSupervisors: Observable<Array<Manager>>;
  public workerForm: FormGroup;
  public workersCtrl: FormControl = new FormControl();
  public supervisorCtrl: FormControl = new FormControl();

  constructor(
    private _employeeService: EmployeeService,
    private _managerService: ManagerService,
    private _hrTeamMemberService: HrTeamMemberService,
    private _responsiveHelper: ResponsiveHelperService,
    private _notificationService: NotificationService,
    private _errorResolver: ErrorResolverService,
    private _fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.fetchWorkers();
  }

  ngOnDestroy(): void {
    this.unsubscribeAll();
  }

  private unsubscribeAll(): void {
    if (this.$workers) {
      this.$workers.unsubscribe();
    }
    if (this.$employees) {
      this.$employees.unsubscribe();
    }
    if (this.$managers) {
      this.$managers.unsubscribe();
    }
    if (this.$hrTeamMembers) {
      this.$hrTeamMembers.unsubscribe();
    }
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

    this.workerForm = this._fb.group({
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
        position: [employeeInfo.position, Validators.required],
        department: [employeeInfo.department],
        employeeNumber: [employeeInfo.employeeNumber, Validators.required],
        startDate: [employeeInfo.startDate],
        endDate: [employeeInfo.endDate],
      }),
      hrInformation: this._fb.group({
        allowance: [hrInfo.allowance, Validators.compose([Validators.min(0), Validators.required])],
        usedAllowance: [hrInfo.usedAllowance, Validators.compose([Validators.min(0), Validators.required])],
      }),
      role: [this.subject.role],
    });

    this.buildSupervisorCtrl(this.subject);
  }

  public buildSupervisorCtrl(subject: Subject): void {
    const workerRole = `ROLE_${subject.role}`;
    switch (workerRole) {
      case Role.EMPLOYEE:
        const employee: Employee = <Employee>subject;
        this.supervisorCtrl.setValue(employee.manager);
        this.supervisorCtrl.setValidators(Validators.required);
        break;
      case Role.MANAGER:
        const manager: Manager = <Manager>subject;
        this.supervisorCtrl.setValue(manager.hrTeamMember);
        this.supervisorCtrl.setValidators(Validators.required);
        break;
      case Role.HRTEAMMEMBER:
        this.supervisorCtrl.setValidators(null);
        this.supervisorCtrl.updateValueAndValidity();
        break;
    }
  }

  public reduceWorkers(subjects: Array<Subject>): Observable<Array<Subject>> {
    return this.workersCtrl.valueChanges.pipe(
      startWith(''),
      map(lastName => (typeof lastName === 'string' ? lastName : '')),
      map(subject => (subject ? this.filterSubjects(subjects, subject) : subjects.slice()))
    );
  }

  public reduceSupervisors(supervisor: Array<Subject>): Observable<Array<Subject>> {
    return this.supervisorCtrl.valueChanges.pipe(
      startWith(''),
      map(lastName => (typeof lastName === 'string' ? lastName : '')),
      map(subject => (subject ? this.filterSubjects(supervisor, subject) : supervisor.slice()))
    );
  }

  public filterSubjects(subjects: Array<Subject>, lastName: string): Array<Subject> {
    return subjects.filter(subject => subject.personalInformation.lastName.toLowerCase().indexOf(lastName.toLowerCase()) === 0);
  }

  public fetchWorkers(): void {
    this.$workers = Observable.zip(
      this._employeeService.getEmployees(),
      this._managerService.getManagers(),
      this._hrTeamMemberService.getHrTeamMembers(),
      (employees: Array<Employee>, managers: Array<Manager>, hrTeamMembers: Array<HrTeamMember>) => ({ employees, managers, hrTeamMembers })
    ).subscribe(
      pair => {
        this.employees = pair.employees;
        this.managers = pair.managers;
        this.hrTeamMembers = pair.hrTeamMembers;
        const workers: Array<Subject> = pair.employees.concat(pair.managers).concat(pair.hrTeamMembers);
        this.filteredWorkers = this.reduceWorkers(workers);
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public fetchSelectedEmployee(employeeId: number): void {
    this.isLoadingResults = true;
    this.$employees = this._employeeService.getEmployee(employeeId).subscribe(
      (response: Employee) => {
        this.subject = response;
        this.constructForm();
        this.isLoadingResults = false;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public fetchSelectedManager(workerId: number): void {
    this.isLoadingResults = true;
    this.$employees = this._managerService.getManager(workerId).subscribe(
      (response: Manager) => {
        this.subject = response;
        this.constructForm();
        this.isLoadingResults = false;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public fetchSelectedHrTeamMember(workerId: number) {
    this.isLoadingResults = true;
    this.$employees = this._hrTeamMemberService.getHrTeamMember(workerId).subscribe(
      (response: HrTeamMember) => {
        this.subject = response;
        this.constructForm();
        this.isLoadingResults = false;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public fetchManagers(): void {
    this.isLoadingResults = true;
    this.$managers = this._managerService.getManagers().subscribe(
      (response: Array<Manager>) => {
        this.managers = response;
        this.filteredSupervisors = this.reduceSupervisors(response);
        this.isLoadingResults = false;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public fetchHrTeamMembers(): void {
    this.isLoadingResults = true;
    this.$hrTeamMembers = this._hrTeamMemberService.getHrTeamMembers().subscribe(
      (response: Array<HrTeamMember>) => {
        this.hrTeamMembers = response;
        this.filteredSupervisors = this.reduceSupervisors(response);
        this.isLoadingResults = false;
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
    const worker: Subject = <Subject>$event.option.value;
    const workerRole = `ROLE_${worker.role}`;
    if (workerRole === Role.EMPLOYEE) {
      this.fetchSelectedEmployee(worker.subjectId);
      this.fetchManagers();
    } else if (workerRole === Role.MANAGER) {
      this.fetchSelectedManager(worker.subjectId);
      this.fetchHrTeamMembers();
    } else if (workerRole === Role.HRTEAMMEMBER) {
      this.fetchSelectedHrTeamMember(worker.subjectId);
    }
  }

  public save(): void {
    const workerRole = `ROLE_${this.subject.role}`;
    switch (workerRole) {
      case Role.EMPLOYEE:
        this.updateEmployee();
        break;
      case Role.MANAGER:
        this.updateManager();
        break;
      case Role.HRTEAMMEMBER:
        this.updateHrTeamMember();
        break;
    }
  }

  public updateEmployee(): void {
    const updatedEmployee: Employee = <Employee>this.workerForm.value;
    const updatedManger: Manager = <Manager>this.supervisorCtrl.value;
    this.$employees = Observable.zip(
      this._employeeService.updateEmployee(updatedEmployee),
      this._employeeService.updateEmployeesManager(updatedEmployee.subjectId, updatedManger),
      (employee: Employee, manager: Manager) => ({ employee, manager })
    ).subscribe(
      pair => {
        const msg = `Employee with id ${pair.employee.subjectId} has been updated`;
        this._notificationService.openSnackBar(msg, 'OK');
        this.subject = pair.employee;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public updateManager(): void {
    const updatedManager: Manager = <Manager>this.workerForm.value;
    const updatedHrTeamMember: HrTeamMember = <HrTeamMember>this.supervisorCtrl.value;
    this.$managers = Observable.zip(
      this._managerService.updateManager(updatedManager),
      this._managerService.updateManagerHrTeamMember(updatedManager.subjectId, updatedHrTeamMember.subjectId),
      (manager: Manager, hrTeamMember: HrTeamMember) => ({ manager, hrTeamMember })
    ).subscribe(
      pair => {
        const msg = `Manager with id ${pair.manager.subjectId} has been updated`;
        this._notificationService.openSnackBar(msg, 'OK');
        this.subject = pair.manager;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public updateHrTeamMember(): void {
    const updatedHrTeamMember: HrTeamMember = <HrTeamMember>this.workerForm.value;
    this.$hrTeamMembers = this._hrTeamMemberService.updateHrTeamMember(updatedHrTeamMember).subscribe(
      (hrTeamMember: HrTeamMember) => {
        const msg = `HR Team Member with id ${hrTeamMember.subjectId} has been updated`;
        this._notificationService.openSnackBar(msg, 'OK');
        this.subject = hrTeamMember;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }

  public isValid(): boolean {
    return this.workerForm.valid && this.supervisorCtrl.valid;
  }
}
