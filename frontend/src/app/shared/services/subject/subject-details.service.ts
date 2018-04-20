import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { Subject } from '../../domain/subject/subject';
import { JwtHelperService } from '../jwt/jwt-helper.service';

@Injectable()
export class SubjectDetailsService {
  private url: string = SystemVariables.API_URL + '/subjects';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
  });

  constructor(private _http: HttpClient, private _jwtHelper: JwtHelperService) {}

  public getCurrentSubject(): Observable<Subject> {
    return this._http.get<Subject>(`${this.url}/${this._jwtHelper.getSubjectId()}`, {
      headers: this.headers,
    });
  }

  public getSubjectById(subjectId: number): Observable<Subject> {
    return this._http.get<Subject>(`${this.url}/${subjectId}`, {
      headers: this.headers,
    });
  }
}
