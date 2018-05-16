import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { Password } from '@modules/settings/pages/account/domain/password';
import { Email } from '@modules/settings/pages/account/domain/email';

@Injectable()
export class AccountService {
  private readonly baseUrl: string = SystemVariables.API_URL;
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
  });

  constructor(private _http: HttpClient, private _jwtHelper: JwtHelperService) {}

  public updatePassword(password: Password): Observable<any> {
    const subjectId: number = this._jwtHelper.getSubjectId();
    return this._http.put(`${this.baseUrl}/users/auth/${subjectId}`, password, {
      headers: this.headers,
    });
  }

  public updateEmail(email: Email): Observable<any> {
    const subjectId: number = this._jwtHelper.getSubjectId();
    return this._http.put(`${this.baseUrl}/subjects/${subjectId}/email`, email, {
      headers: this.headers,
    });
  }
}
