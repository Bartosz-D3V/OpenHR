import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';

@Injectable()
export class TokenExpirationService {
  private readonly url: string = SystemVariables.API_URL + '/auth/token';

  constructor(private _http: HttpClient) {}

  public refreshToken(token: string): Observable<HttpResponse<null>> {
    const headers: HttpHeaders = new HttpHeaders({
      Authorization: `Bearer-${token}`,
    });
    return this._http.post<null>(this.url, {
      headers: headers,
    });
  }
}
