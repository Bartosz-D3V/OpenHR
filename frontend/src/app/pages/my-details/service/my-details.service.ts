import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/timeout';

import { Subject } from '../classes/subject';
import { ErrorResolverService } from '../../../shared/services/error-resolver/error-resolver.service';

@Injectable()
export class MyDetailsService {

  private url = 'app/my-details';
  private headers: Headers = new Headers({
    'Content-Type': 'application/json',
  });
  private options: RequestOptions = new RequestOptions({
    headers: this.headers,
  });

  private handleError(error: any): void {
    console.log('An error occurred', error);
    this._errorResolver.createAlert(error);
  }

  constructor(private _http: Http,
              private _errorResolver: ErrorResolverService) {
  }

  public getCurrentSubject(): Observable<Subject> {
    return this._http
      .get(this.url)
      .map((response: Response) => <Subject> response.json())
      .timeout(5000, null)
      .catch((error: any) => {
        this.handleError(error);
        return Observable.of(error);
      });
  }

}
