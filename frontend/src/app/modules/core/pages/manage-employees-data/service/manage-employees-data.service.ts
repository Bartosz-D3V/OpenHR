import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared//services/jwt/jwt-helper.service';
import { Subject } from '@shared/domain/subject/subject';

@Injectable()
export class ManageEmployeesDataService {
  private readonly baseUrl: string = SystemVariables.API_URL;
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
    Authorization: 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient, private _jwtHelper: JwtHelperService) {}

  public getSupervisors(): Observable<Array<Subject>> {
    return this._http.get<Array<Subject>>(`${this.baseUrl}/supervisors`, {
      headers: this.headers,
    });
  }

  public updateSubjectsSupervisor(subjectId: number, supervisorId: number): Observable<void> {
    const params: HttpParams = new HttpParams().set('supervisorId', supervisorId.toString());
    return this._http.post<void>(`${this.baseUrl}/subjects/${subjectId}`, {
      headers: this.headers,
      params: params,
    });
  }
}
