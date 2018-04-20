import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';

@Injectable()
export class WorkersService {
  private url: string = SystemVariables.API_URL + '/subjects';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
  });

  constructor(private _http: HttpClient) {}

  public getWorkers(): Observable<Array<LightweightSubject>> {
    return this._http.get<Array<LightweightSubject>>(`${this.url}/lightweight`, {
      headers: this.headers,
    });
  }
}
