import { Component, OnInit } from '@angular/core';
import {MatPaginator, MatSort} from '@angular/material';

import { EmployeeData } from './employee-data';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.scss'],
})
export class EmployeesComponent implements OnInit {
  tableColumns: Array<string> = ['id', 'Name', 'Position'];

  constructor() { }

  ngOnInit() {
  }

}
