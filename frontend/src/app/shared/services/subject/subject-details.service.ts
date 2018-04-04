import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

import { ErrorResolverService } from '../error-resolver/error-resolver.service';
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

  private handleError(error: any): void {
    this._errorResolver.createAlert(error);
  }

  constructor(private _http: HttpClient, private _jwtHelper: JwtHelperService, private _errorResolver: ErrorResolverService) {}

  public getCurrentSubject(): Observable<Subject> {
    return this._http
      .get<Subject>(`${this.url}/${this._jwtHelper.getSubjectId()}`, {
        headers: this.headers,
      })
      .catch((error: any) => {
        this.handleError(error);
        return Observable.of(error);
      });
  }

  public getSubjectById(subjectId: number): Observable<Subject> {
    return this._http
      .get<Subject>(`${this.url}/${subjectId}`, {
        headers: this.headers,
      })
      .catch((error: any) => {
        this.handleError(error);
        return Observable.of(error);
      });
  }

  public createSubject(subject: Subject): Observable<Subject> {
    return this._http
      .post(this.url, subject, {
        headers: this.headers,
      })
      .catch((error: any) => {
        this.handleError(error);
        return Observable.of(error);
      });
  }
}
