import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ISubscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';
import { map } from 'rxjs/operator/map';
import { startWith } from 'rxjs/operator/startWith';

import { RegularExpressions } from '../../../../shared/constants/regexps/regular-expressions';
import { SubjectDetailsService } from '../../../../shared/services/subject/subject-details.service';
import { Subject } from '../../../../shared/domain/subject/subject';
import { Employee } from '../employees/domain/employee';
import { ManageEmployeesDataService } from './service/manage-employees-data.service';

@Component({
  selector: 'app-manage-employees-data',
  templateUrl: './manage-employees-data.component.html',
  styleUrls: ['./manage-employees-data.component.scss'],
  providers: [
    ManageEmployeesDataService,
    SubjectDetailsService,
  ],
})
export class ManageEmployeesDataComponent implements OnInit, OnDestroy {
  private $employees: ISubscription;
  private $subject: ISubscription;
  employees: Array<Employee>;
  subject: Subject;
  employeeForm: FormGroup;
  employeesCtrl: FormControl;

  constructor(private _manageEmployeesDataService: ManageEmployeesDataService,
              private _subjectService: SubjectDetailsService,
              private _fb: FormBuilder) {
  }

  ngOnInit() {
    this.constructForm();
    this.fetchEmployees();
  }

  ngOnDestroy(): void {
    this.$employees.unsubscribe();
    this.$subject.unsubscribe();
  }

  private constructForm(): void {
    this.employeeForm = this._fb.group({
      personalInformation: this._fb.group({
        firstName: [Validators.required],
        lastName: [Validators.required],
        middleName: [],
        dob: [Validators.required],
        position: [],
      }),
      contactInformation: this._fb.group({
        email: [
          Validators.required,
          Validators.pattern(RegularExpressions.EMAIL)],
        telephone: ['',
          Validators.required,
          Validators.pattern(RegularExpressions.NUMBERS_ONLY),
          Validators.minLength(7),
          Validators.maxLength(11)],
        firstLineAddress: [],
        secondLineAddress: [],
        thirdLineAddress: [],
        postcode: ['',
          Validators.required,
          Validators.pattern(RegularExpressions.UK_POSTCODE)],
        city: [],
        country: [],
      }),
      employeeInformation: this._fb.group({
        nin: ['',
          Validators.required,
          Validators.pattern(RegularExpressions.NIN)],
        employeeId: ['', Validators.required],
        startDate: [],
        endDate: [],
      }),
      hrInformation: this._fb.group({
        allowance: ['', Validators.min(0)],
        usedAllowance: ['', Validators.min(0)],
        manager: ['', Validators.required],
      }),
    });
  }

  reduceEmployees(employees: Array<Employee>): Observable<Array<Employee>> {
    this.employeesCtrl = new FormControl();
    return this.employeesCtrl
      .valueChanges
      .startWith(null)
      .map(lastName => lastName ? this.filterEmployees(employees, lastName) : employees.slice());
  }

  filterEmployees(employees: Array<Employee>, lastName: string): Array<Employee> {
    return employees.filter(employee =>
    employee.subject.lastName.toLowerCase().indexOf(lastName.toLowerCase()) === 0);
  }

  fetchEmployees(): void {
    this.$employees = this._manageEmployeesDataService
      .getEmployees()
      .subscribe((response: Array<Employee>) => {
        this.employees = response;
        this.reduceEmployees(response);
      });
  }

  fetchSubject(subjectId: number): void {
    this.$subject = this._subjectService
      .getSubjectById(subjectId)
      .subscribe((result: Subject) => this.subject = result);
  }

  save(): void {

  }
}
