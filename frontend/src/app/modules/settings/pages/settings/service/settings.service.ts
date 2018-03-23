import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';

import {SystemVariables} from '@config/system-variables';
import {ErrorResolverService} from '@shared//services/error-resolver/error-resolver.service';

@Injectable()
export class SettingsService {
  private url: string = SystemVariables.API_URL + 'user-settings';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
  });
  private params: HttpParams;

  private handleError(error: any): void {
    this._errorResolver.createAlert(error);
  }

  constructor(private _http: HttpClient, private _errorResolver: ErrorResolverService) {}

  public setNotificationsSetting(subjectId: number, notificationsTurn: string): Observable<boolean> {
    this.params = new HttpParams().set('subjectId', subjectId.toString()).set('notificationsTurn', notificationsTurn);
    return this._http
      .put<boolean>(this.url, {
        headers: this.headers,
        params: this.params,
      })
      .catch((error: any) => {
        this.handleError(error);
        return Observable.of(error);
      });
  }

  public getNotificationsSetting(subjectId: number): Observable<boolean> {
    this.params = new HttpParams().set('subjectId', subjectId.toString());
    return this._http
      .get<boolean>(this.url, {
        headers: this.headers,
        params: this.params,
      })
      .catch((error: any) => {
        this.handleError(error);
        return Observable.of(error);
      });
  }
}
