import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs/Observable';
import { ISubscription } from 'rxjs/Subscription';
import { map, startWith } from 'rxjs/operators';
import { MatAutocompleteSelectedEvent } from '@angular/material';

import { RegularExpressions } from '../../../../shared/constants/regexps/regular-expressions';
import { ResponsiveHelperService } from '../../../../shared/services/responsive-helper/responsive-helper.service';
import { NotificationService } from '../../../../shared/services/notification/notification.service';
import { Subject } from '../../../../shared/domain/subject/subject';
import { Employee } from '../../../../shared/domain/subject/employee';
import { PersonalInformation } from '../../../../shared/domain/subject/personal-information';
import { ContactInformation } from '../../../../shared/domain/subject/contact-information';
import { EmployeeInformation } from '../../../../shared/domain/subject/employee-information';
import { HrInformation } from '../../../../shared/domain/subject/hr-information';
import { ManageEmployeesDataService } from './service/manage-employees-data.service';
import { EmployeeService } from '../../../../shared/services/employee/employee.service';
import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';

@Component({
  selector: 'app-manage-employees-data',
  templateUrl: './manage-employees-data.component.html',
  styleUrls: ['./manage-employees-data.component.scss'],
  providers: [
    ManageEmployeesDataService,
    EmployeeService,
    ResponsiveHelperService,
    NotificationService,
  ],
})
export class ManageEmployeesDataComponent implements OnInit, OnDestroy {
  private $employees: ISubscription;
  stepNumber = 0;
  employees: Array<Employee>;
  subject: Subject;
  filteredEmployees: Observable<Array<Subject>>;
  employeeForm: FormGroup;
  employeesCtrl: FormControl = new FormControl();

  constructor(private _manageEmployeesDataService: ManageEmployeesDataService,
              private _employeeService: EmployeeService,
              private _responsiveHelper: ResponsiveHelperService,
              private _notificationService: NotificationService,
              private _errorResolver: ErrorResolverService,
              private _fb: FormBuilder) {
  }

  ngOnInit() {
    this.fetchEmployees();
  }

  ngOnDestroy(): void {
    this.$employees.unsubscribe();
  }

  setStep(stepNumber: number): void {
    this.stepNumber = stepNumber;
  }

  nextStep(): void {
    this.stepNumber++;
  }

  prevStep(): void {
    this.stepNumber--;
  }

  private constructForm(): void {
    const perInfo: PersonalInformation = this.subject.personalInformation;
    const contactInfo: ContactInformation = this.subject.contactInformation;
    const employeeInfo: EmployeeInformation = this.subject.employeeInformation;
    const hrInfo: HrInformation = this.subject.hrInformation;
    this.employeeForm = this._fb.group({
      personalInformation: this._fb.group({
        firstName: [perInfo.firstName,
          Validators.required],
        middleName: [perInfo.middleName],
        lastName: [perInfo.lastName,
          Validators.required],
        dob: [perInfo.dateOfBirth,
          Validators.required],
      }),
      contactInformation: this._fb.group({
        telephone: [contactInfo.telephone,
          Validators.required,
          Validators.pattern(RegularExpressions.NUMBERS_ONLY),
          Validators.minLength(7),
          Validators.maxLength(11)],
        email: [contactInfo.email,
          Validators.required,
          Validators.pattern(RegularExpressions.EMAIL)],
        firstLineAddress: [contactInfo.address.firstLineAddress],
        secondLineAddress: [contactInfo.address.secondLineAddress],
        thirdLineAddress: [contactInfo.address.thirdLineAddress],
        postcode: [contactInfo.address.postcode,
          Validators.required,
          Validators.pattern(RegularExpressions.UK_POSTCODE)],
        city: [contactInfo.address.city],
        country: [contactInfo.address.country],
      }),
      employeeInformation: this._fb.group({
        nin: [employeeInfo.nationalInsuranceNumber,
          Validators.required,
          Validators.pattern(RegularExpressions.NIN)],
        position: [employeeInfo.position],
        employeeNumber: [employeeInfo.employeeNumber,
          Validators.required],
        startDate: [employeeInfo.startDate],
        endDate: [employeeInfo.endDate],
      }),
      hrInformation: this._fb.group({
        allowance: [hrInfo.allowance,
          Validators.min(0)],
        usedAllowance: [hrInfo.usedAllowance,
          Validators.min(0)],
        manager: [Validators.required],
      }),
    });
  }

  reduceEmployees(employees: Array<Employee>): Observable<Array<Subject>> {
    this.filteredEmployees = this.employeesCtrl
      .valueChanges
      .pipe(
        startWith(''),
        map(lastName => typeof lastName === 'string' ? lastName : ''),
        map(employee => employee ? this.filterEmployees(employees, employee) : employees.slice())
      );
    return this.filteredEmployees;
  }

  filterEmployees(employees: Array<Employee>, lastName: string): Array<Employee> {
    return employees.filter(employee =>
    employee.personalInformation.lastName.toLowerCase().indexOf(lastName.toLowerCase()) === 0);
  }

  fetchEmployees(): void {
    this.$employees = this._manageEmployeesDataService
      .getEmployees()
      .subscribe((response: Array<Employee>) => {
        this.employees = response;
        this.reduceEmployees(response);
      });
  }

  displayFullName(subject: Subject): string | undefined {
    return subject && subject.personalInformation ?
      `${subject.personalInformation.firstName} ${subject.personalInformation.lastName}` : undefined;
  }

  displaySubject($event: MatAutocompleteSelectedEvent): void {
    this.subject = $event.option.value;
    this.constructForm();
  }

  save(): void {
    this.subject = this.employeeForm.value;
    this._employeeService.updateEmployee(this.subject)
      .subscribe((res: Employee) => {
        const msg = `Employee with id ${res.subjectId} has been updated`;
        this._notificationService.openSnackBar(msg, 'OK');
      }, (err: any) => {
        this._errorResolver.createAlert(err);
      });
  }

  isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }

  isValid(): boolean {
    return this.employeeForm.valid;
  }
}
