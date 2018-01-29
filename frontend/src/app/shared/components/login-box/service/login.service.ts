import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '../../../../config/system-variables';
import { Credentials } from '../domain/credentials';

@Injectable()
export class LoginService {
  private url: string = SystemVariables.API_URL + 'login/';
  private headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  constructor(private _http: HttpClient) {
  }

  public login(credentials: Credentials): Observable<Response> {
    return this._http
      .post<Response>(this.url, credentials, {
        headers: this.headers,
      });
  }

}
