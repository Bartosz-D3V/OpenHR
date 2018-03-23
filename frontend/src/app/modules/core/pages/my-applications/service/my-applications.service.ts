import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

import {SystemVariables} from '@config/system-variables';
import {JwtHelperService} from '@shared//services/jwt/jwt-helper.service';
import {LeaveApplication} from '@shared//domain/leave-application/leave-application';

@Injectable()
export class MyApplicationsService {
  private url: string = SystemVariables.API_URL + '/leave-application';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
    Authorization: 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient, private _jwtHelper: JwtHelperService) {}

  public getSubmittedLeaveApplications(subjectId: number): Observable<Array<LeaveApplication>> {
    return this._http.get<Array<LeaveApplication>>(`${this.url}/${subjectId}`, {
      headers: this.headers,
    });
  }
}
