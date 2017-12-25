import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '../../../config/system-variables';
import { ErrorResolverService } from '../error-resolver/error-resolver.service';

@Injectable()
export class ConfigService {

  private url: string = SystemVariables.API_URL + 'config/';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
  });

  private handleError(error: any): void {
    console.log('An error occurred', error);
    this._errorResolver.createAlert(error);
  }

  constructor(private _http: HttpClient,
              private _errorResolver: ErrorResolverService) {
  }

  public getContractTypes(): Observable<Array<string>> {
    return this._http
      .get<Array<string>>(this.url + 'contractTypes', {
        headers: this.headers,
      })
      .catch((error: any) => {
        this.handleError(error);
        return Observable.of(error);
      });
  }

  public getLeaveTypes(): Observable<Array<string>> {
    return this._http
      .get<Array<string>>(this.url + 'leaveTypes', {
        headers: this.headers,
      })
      .catch((error: any) => {
        this.handleError(error);
        return Observable.of(error);
      });
  }

}
