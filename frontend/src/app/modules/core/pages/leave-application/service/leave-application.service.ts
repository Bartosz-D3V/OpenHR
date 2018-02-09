import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '../../../../../config/system-variables';
import { JwtHelperService } from '../../../../../shared/services/jwt/jwt-helper.service';
import { ErrorResolverService } from '../../../../../shared/services/error-resolver/error-resolver.service';
import { LeaveType } from '../domain/leave-type';

@Injectable()
export class LeaveApplicationService {

  private url: string = SystemVariables.API_URL + '/leave-application';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer-' + this._jwtHelper.getToken(),
  });

  private handleError(error: any): void {
    console.log('An error occurred', error);
    this._errorResolver.createAlert(error);
  }

  constructor(private _http: HttpClient,
              private _jwtHelper: JwtHelperService,
              private _errorResolver: ErrorResolverService) {
  }

  public getLeaveTypes(): Observable<Array<LeaveType>> {
    return this._http
      .get<Array<LeaveType>>(`${this.url}/types`, {
        headers: this.headers,
      })
      .catch((error: any) => {
        this.handleError(error);
        return Observable.of([]);
      });
  }

}
