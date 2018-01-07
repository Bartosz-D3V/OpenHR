import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';

import { ISubscription } from 'rxjs/Subscription';

import { EmployeesService } from './service/employees.service';
import { EmployeeDataObject } from './domain/employee-data-object';
import { Employee } from './domain/employee';
import { EmployeeData } from './employee-data';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.scss'],
  providers: [EmployeesService],
})
export class EmployeesComponent implements OnInit, OnDestroy, AfterViewInit {
  public employees: Array<EmployeeData>;
  private $employees: ISubscription;
  tableColumns: Array<string> = ['id', 'name', 'position'];
  dataSource: MatTableDataSource<EmployeeData>;

  @ViewChild(MatPaginator)
  paginator: MatPaginator;

  @ViewChild(MatSort)
  sort: MatSort;

  constructor(private _employeesService: EmployeesService) {
    this.dataSource = new MatTableDataSource(this.employees);
  }

  ngOnInit() {
    this.$employees = this._employeesService
      .getEmployees()
      .subscribe((result: Array<Employee>) => {
        this.employees = this.simplifyEmployeeArray(result);
      });
  }

  ngOnDestroy() {
    this.$employees.unsubscribe();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(filterValue: string): void {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  simplifyEmployeeArray(result: Array<Employee>): Array<EmployeeData> {
    const employeeDataArray: Array<EmployeeData> = [];
    for (let i = 0; i < result.length; ++i) {
      employeeDataArray.push(this.simplifyEmployeeObject(result[i]));
    }
    return employeeDataArray;
  }

  simplifyEmployeeObject(employee: Employee): EmployeeData {
    const fullName: string = employee.subject.firstName + ' ' + employee.subject.lastName;
    return new EmployeeDataObject(employee.employeeId, fullName, employee.subject.employeeInformation.position);
  }
}
