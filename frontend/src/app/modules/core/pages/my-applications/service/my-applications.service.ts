import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { LeaveApplication } from '@shared/domain/application/leave-application';
import { DelegationApplication } from '@shared/domain/application/delegation-application';

@Injectable()
export class MyApplicationsService {
  private readonly baseUrl: string = SystemVariables.API_URL;
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
  });

  constructor(private _http: HttpClient) {}

  public getSubmittedLeaveApplications(subjectId: number): Observable<Array<LeaveApplication>> {
    const params: HttpParams = new HttpParams().set('subjectId', subjectId.toString());
    return this._http.get<Array<LeaveApplication>>(`${this.baseUrl}/leave-applications`, {
      params: params,
      headers: this.headers,
    });
  }

  public getSubmittedDelegationApplications(subjectId: number): Observable<Array<DelegationApplication>> {
    const params: HttpParams = new HttpParams().set('subjectId', subjectId.toString());
    return this._http.get<Array<DelegationApplication>>(`${this.baseUrl}/delegations`, {
      params: params,
      headers: this.headers,
    });
  }

  public downloadICS(applicationId: number): Observable<HttpResponse<Array<number>>> {
    const headers: HttpHeaders = new HttpHeaders({
      Accept: 'text/calendar',
    });
    const options: Object = { headers, responseType: 'text/calendar' as 'text/calendar' };
    return this._http.get<HttpResponse<Array<number>>>(`${this.baseUrl}/applications/${applicationId}/ics`, options);
  }
}
