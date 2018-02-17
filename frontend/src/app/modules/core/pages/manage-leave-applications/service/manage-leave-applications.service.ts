import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '../../../../../config/system-variables';
import { ErrorResolverService } from '../../../../../shared/services/error-resolver/error-resolver.service';
import { JwtHelperService } from '../../../../../shared/services/jwt/jwt-helper.service';
import { LeaveApplication } from '../../../../../shared/domain/leave-application/leave-application';

@Injectable()
export class ManageLeaveApplicationsService {
  private url: string = SystemVariables.API_URL + '/leave-application';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient,
              private _jwtHelper: JwtHelperService,
              private _errorResolver: ErrorResolverService) {
  }

  getUnacceptedLeaveApplications(managerId: number): Observable<Array<LeaveApplication>> {
    return this._http
      .get<Array<LeaveApplication>>(`${this.url}/${managerId}/awaiting`, {
        headers: this.headers,
      })
      .catch((error: any) => {
        this._errorResolver.createAlert(error);
        return Observable.of(error);
      });
  }

}
