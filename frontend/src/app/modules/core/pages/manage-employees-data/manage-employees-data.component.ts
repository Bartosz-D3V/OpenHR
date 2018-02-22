import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs/Observable';
import { ISubscription } from 'rxjs/Subscription';
import { map, startWith } from 'rxjs/operators';
import { MatAutocompleteSelectedEvent } from '@angular/material';

import { RegularExpressions } from '../../../../shared/constants/regexps/regular-expressions';
import { SubjectDetailsService } from '../../../../shared/services/subject/subject-details.service';
import { Subject } from '../../../../shared/domain/subject/subject';
import { Employee } from '../../../../shared/domain/subject/employee';
import { ResponsiveHelperService } from '../../../../shared/services/responsive-helper/responsive-helper.service';
import { ManageEmployeesDataService } from './service/manage-employees-data.service';

@Component({
  selector: 'app-manage-employees-data',
  templateUrl: './manage-employees-data.component.html',
  styleUrls: ['./manage-employees-data.component.scss'],
  providers: [
    ManageEmployeesDataService,
    SubjectDetailsService,
    ResponsiveHelperService,
  ],
})
export class ManageEmployeesDataComponent implements OnInit, OnDestroy {
  private $employees: ISubscription;
  private $subject: ISubscription;
  stepNumber = 0;
  employees: Array<Employee>;
  subject: Subject;
  filteredEmployees: Observable<Array<Subject>>;
  employeeForm: FormGroup;
  employeesCtrl: FormControl = new FormControl();

  constructor(private _manageEmployeesDataService: ManageEmployeesDataService,
              private _subjectService: SubjectDetailsService,
              private _responsiveHelper: ResponsiveHelperService,
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
    this.employeeForm = this._fb.group({
      personalInformation: this._fb.group({
        firstName: [Validators.required],
        middleName: [],
        lastName: [Validators.required],
        dob: [Validators.required],
        position: [],
      }),
      contactInformation: this._fb.group({
        telephone: [
          Validators.required,
          Validators.pattern(RegularExpressions.NUMBERS_ONLY),
          Validators.minLength(7),
          Validators.maxLength(11)],
        email: [
          Validators.required,
          Validators.pattern(RegularExpressions.EMAIL)],
        firstLineAddress: [],
        secondLineAddress: [],
        thirdLineAddress: [],
        postcode: [
          Validators.required,
          Validators.pattern(RegularExpressions.UK_POSTCODE)],
        city: [],
        country: [],
      }),
      employeeInformation: this._fb.group({
        nin: [
          Validators.required,
          Validators.pattern(RegularExpressions.NIN)],
        employeeId: [Validators.required],
        startDate: [],
        endDate: [],
      }),
      hrInformation: this._fb.group({
        allowance: [Validators.min(0)],
        usedAllowance: [Validators.min(0)],
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

  fetchSubject(subjectId: number): void {
    this.$subject = this._subjectService
      .getSubjectById(subjectId)
      .subscribe((result: Subject) => this.subject = result);
  }

  save(): void {

  }

  isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }

  isValid(): boolean {
    return true;
  }
}
