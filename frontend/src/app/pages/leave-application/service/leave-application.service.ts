import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';

import { ErrorResolverService } from '../../../shared/services/error-resolver/error-resolver.service';

@Injectable()
export class LeaveApplicationService {

  private url = 'app/leave-application';
  private headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  private handleError(error: any): void {
    console.log('An error occurred', error);
    this._errorResolver.createAlert(error);
  }

  constructor(private _http: HttpClient,
              private _errorResolver: ErrorResolverService) {
  }

  public getLeaveTypes(): Observable<Array<string>> {
    return this._http
      .get<Array<string>>(this.url)
      .catch((error: any) => {
        this.handleError(error);
        return Observable.of([]);
      });
  }

}
