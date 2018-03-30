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

@Component({
  selector: 'app-manage-employees-data',
  templateUrl: './manage-employees-data.component.html',
  styleUrls: ['./manage-employees-data.component.scss'],
  providers: [ManageEmployeesDataService, EmployeeService, ManagerService, ResponsiveHelperService],
})
export class ManageEmployeesDataComponent implements OnInit, OnDestroy {
  private $employees: ISubscription;
  private $employee: ISubscription;
  private $managers: ISubscription;
  public isLoadingResults: boolean;
  public stepNumber = 0;
  public employees: Array<Employee>;
  public managers: Array<Manager>;
  public subject: Employee;
  public filteredEmployees: Observable<Array<Employee>>;
  public filteredManagers: Observable<Array<Manager>>;
  public employeeForm: FormGroup;
  public employeesCtrl: FormControl = new FormControl();
  public managersCtrl: FormControl = new FormControl();

  constructor(
    private _manageEmployeesDataService: ManageEmployeesDataService,
    private _employeeService: EmployeeService,
    private _managerService: ManagerService,
    private _responsiveHelper: ResponsiveHelperService,
    private _notificationService: NotificationService,
    private _errorResolver: ErrorResolverService,
    private _fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.fetchEmployees();
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
    this.managersCtrl.setValue(this.subject.manager);
    this.managersCtrl.setValidators(Validators.required);
  }

  public reduceEmployees(subjects: Array<Employee>): Observable<Array<Subject>> {
    return this.employeesCtrl.valueChanges.pipe(
      startWith(''),
      map(lastName => (typeof lastName === 'string' ? lastName : '')),
      map(subject => (subject ? this.filterSubjects(subjects, subject) : subjects.slice()))
    );
  }

  public reduceManagers(subjects: Array<Manager>): Observable<Array<Subject>> {
    return this.managersCtrl.valueChanges.pipe(
      startWith(''),
      map(lastName => (typeof lastName === 'string' ? lastName : '')),
      map(subject => (subject ? this.filterSubjects(subjects, subject) : subjects.slice()))
    );
  }

  public filterSubjects(subjects: Array<Subject>, lastName: string): Array<Subject> {
    return subjects.filter(subject => subject.personalInformation.lastName.toLowerCase().indexOf(lastName.toLowerCase()) === 0);
  }

  public fetchEmployees(): void {
    this.$employees = this._manageEmployeesDataService.getEmployees().subscribe(
      (response: Array<Employee>) => {
        this.employees = response;
        this.filteredEmployees = this.reduceEmployees(response);
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public fetchSelectedEmployee(employeeId: number): void {
    this.isLoadingResults = true;
    this.$employee = this._employeeService.getEmployee(employeeId).subscribe(
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

  public fetchManagers(): void {
    this.$managers = this._managerService.getManagers().subscribe(
      (response: Array<Manager>) => {
        this.managers = response;
        this.filteredManagers = this.reduceManagers(response);
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
    const employeeId: number = (<Employee>$event.option.value).subjectId;
    this.fetchSelectedEmployee(employeeId);
    this.fetchManagers();
  }

  public save(): void {
    const updatedEmployee: Employee = <Employee>this.employeeForm.value;
    const updatedManger: Manager = <Manager>this.managersCtrl.value;
    Observable.zip(
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

  public isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }

  public isValid(): boolean {
    return this.employeeForm.valid && this.managersCtrl.valid;
  }

  private unsubscribeAll(): void {
    if (this.$employees !== undefined) {
      this.$employees.unsubscribe();
    }
    if (this.$employee !== undefined) {
      this.$employee.unsubscribe();
    }
    if (this.$managers !== undefined) {
      this.$managers.unsubscribe();
    }
  }
}
