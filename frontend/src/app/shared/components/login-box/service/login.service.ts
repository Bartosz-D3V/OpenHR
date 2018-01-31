import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '../../../../config/system-variables';
import { Credentials } from '../domain/credentials';

@Injectable()
export class LoginService {
  private url: string = SystemVariables.API_URL + 'api/auth/login';
  private headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  constructor(private _http: HttpClient) {
  }

  public login(credentials: Credentials): Observable<HttpResponse<null>> {
    return this._http
      .post<null>(this.url, credentials, {
        headers: this.headers,
        observe: 'response',
      });

  }

}