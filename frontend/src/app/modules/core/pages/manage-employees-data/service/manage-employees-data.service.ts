import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared//services/jwt/jwt-helper.service';
import { Employee } from '@shared//domain/subject/employee';

@Injectable()
export class ManageEmployeesDataService {
  private url: string = SystemVariables.API_URL + '/managers';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient,
              private _jwtHelper: JwtHelperService) {
  }

  public getEmployees(): Observable<Array<Employee>> {
    return this._http
      .get<Array<Employee>>(`${this.url}/${this._jwtHelper.getSubjectId()}/employees`, {
        headers: this.headers,
      });
  }
}
