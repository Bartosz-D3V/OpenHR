import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { Manager } from '../../domain/subject/manager';

@Injectable()
export class ManagerService {
  private url: string = SystemVariables.API_URL + '/managers';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
  });

  constructor(private _http: HttpClient) {}

  public getManagers(): Observable<Array<Manager>> {
    return this._http.get<Array<Manager>>(this.url, {
      headers: this.headers,
    });
  }

  public getManager(workerId: number): Observable<Manager> {
    return this._http.get<Manager>(`${this.url}/${workerId}`, {
      headers: this.headers,
    });
  }

  public createManager(manager: Manager): Observable<Manager> {
    return this._http.post<Manager>(this.url, manager, {
      headers: this.headers,
    });
  }

  public updateManager(manager: Manager): Observable<Manager> {
    return this._http.put<Manager>(`${this.url}/${manager.subjectId}`, manager, {
      headers: this.headers,
    });
  }

  public updateManagerHrTeamMember(subjectId: number, hrTeamMemberId: number): Observable<Manager> {
    const params: HttpParams = new HttpParams().set('hrTeamMemberId', hrTeamMemberId.toString());
    return this._http.put<Manager>(`${this.url}/${subjectId}/hr-assignment`, null, {
      headers: this.headers,
      params: params,
    });
  }

  public deleteManager(subjectId: number): Observable<null> {
    return this._http.delete<null>(`${this.url}/${subjectId}`);
  }
}
