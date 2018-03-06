import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '../jwt/jwt-helper.service';
import { Employee } from '../../domain/subject/employee';
import { Manager } from '../../domain/subject/manager';

@Injectable()
export class EmployeeService {
  private url: string = SystemVariables.API_URL + '/employees';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient,
              private _jwtHelper: JwtHelperService) {
  }

  public getEmployee(employeeId: number): Observable<Employee> {
    return this._http
      .get<Employee>(`${this.url}/${employeeId}`, {
        headers: this.headers,
      });
  }

  public updateEmployee(employee: Employee): Observable<Employee> {
    return this._http
      .put<Employee>(`${this.url}/${employee.subjectId}`, employee, {
        headers: this.headers,
      });
  }

  public updateEmployeesManager(employeeId: number, manager: Manager): Observable<Manager> {
    return this._http
      .put<Manager>(`${this.url}/${employeeId}/manager-assignment`, manager, {
        headers: this.headers,
      });
  }
}
