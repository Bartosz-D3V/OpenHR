import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';

@Injectable()
export class LightweightSubjectService {
  private url: string = SystemVariables.API_URL + '/subjects/lightweight';
  private readonly headers: HttpHeaders = new HttpHeaders({
    Accept: 'application/json',
  });

  constructor(private _http: HttpClient) {}

  public getUser(subjectId: number): Observable<LightweightSubject> {
    return this._http.get<LightweightSubject>(`${this.url}/${subjectId}`, {
      headers: this.headers,
    });
  }
}
