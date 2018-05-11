import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';

@Injectable()
export class AsyncValidatorService {
  private readonly url: string = SystemVariables.API_URL + '/users';

  constructor(private _http: HttpClient) {}

  public usernameIsFree(username: string): Observable<HttpResponse<null>> {
    const params: HttpParams = new HttpParams().set('username', username);
    return this._http.head<null>(this.url, {
      params: params,
      observe: 'response',
    });
  }
}
