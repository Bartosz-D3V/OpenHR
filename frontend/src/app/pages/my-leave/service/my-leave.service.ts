import { Injectable } from '@angular/core';
import { Http, RequestOptions, Headers, Response } from '@angular/http';
import { ErrorResolverService } from '../../../shared/services/error-resolver/error-resolver.service';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class MyLeaveService {

  private url = 'app/my-leave';
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

  public getLeaveTypes(): Observable<Array<string>> {
    return this._http
      .get(this.url)
      .map((response: Response) => <Array<string>> response.json())
      .catch((error: any) => {
        this.handleError(error);
        return Observable.of(error);
      });
  }

}
