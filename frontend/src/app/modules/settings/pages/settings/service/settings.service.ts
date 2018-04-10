import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { User } from '@modules/settings/pages/settings/domain/user';

@Injectable()
export class SettingsService {
  private url: string = SystemVariables.API_URL + '/users';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
    Authorization: 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient, private _jwtHelper: JwtHelperService) {}

  public updateUser(user: User): Observable<User> {
    return this._http.put<User>(`${this.url}/${user.userId}`, user, {
      headers: this.headers,
    });
  }

  public getUserBySubjectId(subjectId: number): Observable<User> {
    const params: HttpParams = new HttpParams().set('subjectId', subjectId.toString());
    return this._http.get<User>(this.url, {
      headers: this.headers,
      params: params,
    });
  }
}
