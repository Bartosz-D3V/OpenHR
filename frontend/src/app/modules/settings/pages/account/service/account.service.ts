import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

import {SystemVariables} from '@config/system-variables';
import {JwtHelperService} from '@shared/services/jwt/jwt-helper.service';
import {Password} from '@modules/settings/pages/account/domain/password';

@Injectable()
export class AccountService {
  private readonly url: string = SystemVariables.API_URL + '/users';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
    Authorization: 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient, private _jwtHelper: JwtHelperService) {}

  public updatePassword(password: Password): Observable<any> {
    const subjectId: number = this._jwtHelper.getSubjectId();
    return this._http.put(`${this.url}/auth/${subjectId}`, password, {
      headers: this.headers,
    });
  }
}
