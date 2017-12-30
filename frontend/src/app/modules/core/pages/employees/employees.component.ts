import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';

import { EmployeeData } from './employee-data';
import { Employee } from './domain/employee';
import { EmployeeDataObject } from './domain/employee-data-object';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.scss'],
})
export class EmployeesComponent implements OnInit, AfterViewInit {
  public employees: Array<EmployeeData>;
  tableColumns: Array<string> = ['id', 'name', 'position'];
  dataSource: MatTableDataSource<EmployeeData>;

  @ViewChild(MatPaginator)
  paginator: MatPaginator;

  @ViewChild(MatSort)
  sort: MatSort;

  constructor() {
    this.dataSource = new MatTableDataSource(this.employees);
  }

  ngOnInit() {
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

  simplifyEmployeeObject(employee: Employee): EmployeeData {
    const fullName: string = employee.subject.firstName + ' ' + employee.subject.lastName;
    return new EmployeeDataObject(employee.employeeId, fullName, employee.subject.employeeInformation.position);
  }
}
