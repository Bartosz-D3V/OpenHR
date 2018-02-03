import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SystemVariables } from '../../../../config/system-variables';
import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
import { User } from '../../../../shared/domain/user/user';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class LightweightSubjectService {
  private url: string = SystemVariables.API_URL + 'leave-application';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Accept': 'application/json',
  });

  constructor(private _http: HttpClient,
              private _errorResolver: ErrorResolverService) {
  }

  public getUser(userId: number): Observable<User> {
    return this._http
      .get<User>(this.url, {
        headers: this.headers,
      })
      .catch((error: any) => {
        this._errorResolver.handleError(error);
        return Observable.of(error);
      });
  }
}
