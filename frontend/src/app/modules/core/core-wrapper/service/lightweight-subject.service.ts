import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';

@Injectable()
export class LightweightSubjectService {
  private url: string = SystemVariables.API_URL + '/subjects/lightweight';
  private readonly headers: HttpHeaders = new HttpHeaders({
    Accept: 'application/json',
    Authorization: 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient, private _jwtHelper: JwtHelperService) {}

  public getUser(subjectId: number): Observable<LightweightSubject> {
    return this._http.get<LightweightSubject>(`${this.url}/${subjectId}`, {
      headers: this.headers,
    });
  }
}
