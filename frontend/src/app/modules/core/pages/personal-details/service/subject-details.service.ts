import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

import { ErrorResolverService } from '../../../../../shared/services/error-resolver/error-resolver.service';
import { Subject } from '../domain/subject';
import { SystemVariables } from '../../../../../config/system-variables';

@Injectable()
export class SubjectDetailsService {

  private url: string = SystemVariables.API_URL + 'my-details';
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

  public getCurrentSubject(): Observable<Subject> {
    return this._http
      .get<Subject>(this.url, {
        headers: this.headers,
      })
      .catch((error: any) => {
        this.handleError(error);
        return Observable.of(error);
      });
  }

}
