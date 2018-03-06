import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared//services/jwt/jwt-helper.service';
import { LeaveType } from '@shared//domain/leave-application/leave-type';
import { LeaveApplication } from '@shared//domain/leave-application/leave-application';

@Injectable()
export class LeaveApplicationService {

  private url: string = SystemVariables.API_URL + '/leave-application';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient,
              private _jwtHelper: JwtHelperService) {
  }

  public getLeaveTypes(): Observable<Array<LeaveType>> {
    return this._http
      .get<Array<LeaveType>>(`${this.url}/types`, {
        headers: this.headers,
      });
  }

  public submitLeaveApplication(leaveApplication: LeaveApplication): Observable<LeaveApplication> {
    return this._http
      .post<LeaveApplication>(`${this.url}/${this._jwtHelper.getSubjectId()}`,
        leaveApplication, {
          headers: this.headers,
        });
  }

}
