import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SystemVariables } from '@config/system-variables';
import { Observable } from 'rxjs/Observable';
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

  public createManager(manager: Manager): Observable<Manager> {
    return this._http.post<Manager>(this.url, manager, {
      headers: this.headers,
    });
  }
}
