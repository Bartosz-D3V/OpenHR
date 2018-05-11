import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';

@Injectable()
export class AsyncValidatorService {
  private readonly baseUrl: string = SystemVariables.API_URL;

  constructor(private _http: HttpClient) {}

  public usernameIsFree(username: string): Observable<HttpResponse<null>> {
    const params: HttpParams = new HttpParams().set('username', username);
    return this._http.head<null>(`${this.baseUrl}/users`, {
      params: params,
      observe: 'response',
    });
  }

  public emailIsFree(email: string, excludeEmail?: string): Observable<HttpResponse<null>> {
    const params: HttpParams = new HttpParams().set('email', email).set('excludeEmail', excludeEmail);
    return this._http.head<null>(`${this.baseUrl}/subjects`, {
      params: params,
      observe: 'response',
    });
  }
}
