import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared//services/jwt/jwt-helper.service';
import { LeaveApplication } from '@shared//domain/application/leave-application';
import { DelegationApplication } from '@shared/domain/application/delegation-application';

@Injectable()
export class MyApplicationsService {
  private readonly baseUrl: string = SystemVariables.API_URL;
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient,
              private _jwtHelper: JwtHelperService) {
  }

  public getSubmittedLeaveApplications(subjectId: number): Observable<Array<LeaveApplication>> {
    return this._http
      .get<Array<LeaveApplication>>(`${this.baseUrl}/leave-application/${subjectId}`, {
        headers: this.headers,
      });
  }

  public getSubmittedDelegationApplications(subjectId: number): Observable<Array<DelegationApplication>> {
    return this._http
      .get<Array<DelegationApplication>>(`${this.baseUrl}/delegations/${subjectId}`, {
        headers: this.headers,
      });
  }

}
