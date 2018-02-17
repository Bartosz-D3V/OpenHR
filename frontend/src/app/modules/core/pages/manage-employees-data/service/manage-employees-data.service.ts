import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '../../../../../config/system-variables';
import { ErrorResolverService } from '../../../../../shared/services/error-resolver/error-resolver.service';
import { JwtHelperService } from '../../../../../shared/services/jwt/jwt-helper.service';
import { Employee } from '../../employees/domain/employee';

@Injectable()
export class ManageEmployeesDataService {
  private url: string = SystemVariables.API_URL + '/employees';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer-' + this._jwtHelper.getToken(),
  });
  private params: HttpParams;

  constructor(private _http: HttpClient,
              private _jwtHelper: JwtHelperService,
              private _errorResolver: ErrorResolverService) {
  }

  public getEmployees(): Observable<Array<Employee>> {
    this.params = new HttpParams()
      .set('subjectId', this._jwtHelper.getSubjectId().toString());
    return this._http
      .get<Array<Employee>>(this.url, {
        headers: this.headers,
        params: this.params,
      })
      .catch((error: any) => {
        this._errorResolver.createAlert(error);
        return Observable.of([]);
      });
  }

}
