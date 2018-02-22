import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '../../../../../config/system-variables';
import { ErrorResolverService } from '../../../../../shared/services/error-resolver/error-resolver.service';
import { JwtHelperService } from '../../../../../shared/services/jwt/jwt-helper.service';
import { Subject } from '../../../../../shared/domain/subject/subject';
import { Role } from '../../../../../shared/domain/subject/role';

@Injectable()
export class PersonalDetailsService {
  private baseUrl: string = SystemVariables.API_URL;
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient,
              private _jwtHelper: JwtHelperService,
              private _errorResolver: ErrorResolverService) {
  }

  public saveSubject(subject: Subject): Observable<Subject> {
    return this._http
      .put(this.resolveUrl(subject), subject, {
        headers: this.headers,
      })
      .catch((error: any) => {
        this._errorResolver.handleError(error);
        return Observable.of(error);
      });
  }

  resolveUrl(subject: Subject): string {
    let url: string = this.baseUrl;
    switch (subject.role) {
      case Role.EMPLOYEE:
      default:
        url = url.concat(`/employees/${subject.subjectId}`);
        break;
      case Role.MANAGER:
        url = url.concat(`/managers/${subject.subjectId}`);
        break;
    }
    return url;
  }

}
