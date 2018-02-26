import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

import {SystemVariables} from '../../../../../config/system-variables';
import {JwtHelperService} from '../../../../../shared/services/jwt/jwt-helper.service';
import {LeaveApplication} from '../../../../../shared/domain/leave-application/leave-application';

@Injectable()
export class ManageLeaveApplicationsService {
  private url: string = SystemVariables.API_URL + '/leave-application';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient,
              private _jwtHelper: JwtHelperService) {
  }

  public getAwaitingForManagerLeaveApplications(managerId: number): Observable<Array<LeaveApplication>> {
    return this._http
      .get<Array<LeaveApplication>>(`${this.url}/${managerId}/awaiting`, {
        headers: this.headers,
      });
  }

  public approveLeaveApplicationByManager(processInstanceId: string): Observable<any> {
    const params: HttpParams = new HttpParams()
      .set('processInstanceId', processInstanceId);
    return this._http
      .put(`${this.url}/approve`, null, {
        params: params,
        headers: this.headers,
      });
  }

  public rejectLeaveApplicationByManager(processInstanceId: string): Observable<any> {
    const params: HttpParams = new HttpParams()
      .set('processInstanceId', processInstanceId);
    return this._http
      .put(`${this.url}/reject`, null, {
        headers: this.headers,
        params: params,
      });
  }

}
