import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';

import { User } from '@shared/domain/user/user';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';

@Injectable()
export class LightweightSubjectService {
  private url: string = SystemVariables.API_URL + '/subjects/lightweight';
  private readonly headers: HttpHeaders = new HttpHeaders({
    Accept: 'application/json',
    Authorization: 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient, private _jwtHelper: JwtHelperService, private _errorResolver: ErrorResolverService) {}

  public getUser(userId: number): Observable<User> {
    return this._http
      .get<User>(`${this.url}/${userId}`, {
        headers: this.headers,
      })
      .catch((error: any) => {
        this._errorResolver.handleError(error);
        return Observable.of(error);
      });
  }
}
