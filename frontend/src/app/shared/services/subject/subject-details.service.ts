import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

import { SystemVariables } from '@config/system-variables';
import { Subject } from '../../domain/subject/subject';
import { JwtHelperService } from '../jwt/jwt-helper.service';

@Injectable()
export class SubjectDetailsService {
  private url: string = SystemVariables.API_URL + '/subjects';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
    Authorization: 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient, private _jwtHelper: JwtHelperService) {}

  public getSubjects(): Observable<Array<Subject>> {
    return this._http.get<Array<Subject>>(this.url, {
      headers: this.headers,
    });
  }

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

  public createSubject(subject: Subject): Observable<Subject> {
    return this._http.post<Subject>(this.url, subject, {
      headers: this.headers,
    });
  }

  public updateSubject(updatedSubject: Subject): Observable<Subject> {
    return this._http.put<Subject>(`${this.url}/${updatedSubject.subjectId}`, updatedSubject, {
      headers: this.headers,
    });
  }
}
