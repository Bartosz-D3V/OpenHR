import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '../../../config/system-variables';
import { Employee } from '../../domain/subject/employee';
import { JwtHelperService } from '../jwt/jwt-helper.service';

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

  public updateEmployee(employee: Employee): Observable<Employee> {
    return this._http
      .put<Employee>(`${this.url}/${employee.subjectId}`, employee, {
        headers: this.headers,
      });
  }
}
