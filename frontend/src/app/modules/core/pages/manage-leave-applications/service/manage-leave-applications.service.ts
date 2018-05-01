import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared//services/jwt/jwt-helper.service';
import { LeaveApplication } from '@shared//domain/application/leave-application';
import { Role } from '@shared//domain/subject/role';

@Injectable()
export class ManageLeaveApplicationsService {
  private url: string = SystemVariables.API_URL + '/leave-applications';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
  });

  constructor(private _http: HttpClient, private _jwtHelper: JwtHelperService) {}

  public getAwaitingForActionLeaveApplications(subjectId: number): Observable<Array<LeaveApplication>> {
    const params: HttpParams = new HttpParams().set('subjectId', subjectId.toString());
    return this._http.get<Array<LeaveApplication>>(`${this.url}/awaiting`, {
      headers: this.headers,
      params: params,
    });
  }

  public approveLeaveApplicationByManager(processInstanceId: string): Observable<any> {
    const url: string = this.getUrlByRole();
    const params: HttpParams = new HttpParams().set('processInstanceId', processInstanceId);
    return this._http.put(`${this.url}/${url}-approve`, null, {
      params: params,
      headers: this.headers,
    });
  }

  public rejectLeaveApplicationByManager(processInstanceId: string, refusalReason: string): Observable<any> {
    const url: string = this.getUrlByRole();
    const params: HttpParams = new HttpParams().set('processInstanceId', processInstanceId);
    return this._http.put(`${this.url}/${url}-reject`, refusalReason, {
      headers: this.headers,
      params: params,
    });
  }

  private getUrlByRole(): string {
    const role: Role = this._jwtHelper.getUsersRole()[0];
    switch (role) {
      case Role.MANAGER:
        return 'manager';
      case Role.HRTEAMMEMBER:
        return 'hr';
    }
  }
}
