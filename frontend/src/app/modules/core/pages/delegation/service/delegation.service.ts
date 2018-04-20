import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { DelegationApplication } from '@shared/domain/application/delegation-application';
import { Country } from '@shared/domain/country/country';

@Injectable()
export class DelegationService {
  private readonly url: string = SystemVariables.API_URL + '/delegations';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
  });

  constructor(private _http: HttpClient, private _jwtHelper: JwtHelperService) {}

  public getCountries(): Observable<Array<Country>> {
    return this._http.get<Array<Country>>(`${this.url}/countries`, {
      headers: this.headers,
    });
  }

  public createDelegationApplication(application: DelegationApplication): Observable<DelegationApplication> {
    const params: HttpParams = new HttpParams().set('subjectId', this._jwtHelper.getSubjectId().toString());
    return this._http.post<DelegationApplication>(this.url, application, {
      params: params,
      headers: this.headers,
    });
  }

  public updateDelegationApplication(application: DelegationApplication): Observable<DelegationApplication> {
    const params: HttpParams = new HttpParams().set('processInstanceId', application.processInstanceId);
    return this._http.put<DelegationApplication>(this.url, application, {
      params: params,
      headers: this.headers,
    });
  }

  public getDelegationApplication(applicationId: number): Observable<DelegationApplication> {
    return this._http.get<DelegationApplication>(`${this.url}/${applicationId}`, {
      headers: this.headers,
    });
  }
}
