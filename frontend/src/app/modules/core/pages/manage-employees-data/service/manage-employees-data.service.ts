import { Injectable } from '@angular/core';
import { SystemVariables } from '../../../../../config/system-variables';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ErrorResolverService } from '../../../../../shared/services/error-resolver/error-resolver.service';
import { JwtHelperService } from '../../../../../shared/services/jwt/jwt-helper.service';
import { Observable } from 'rxjs/Observable';
import { Employee } from '../../employees/domain/employee';

@Injectable()
export class ManageEmployeesDataService {
  private url: string = SystemVariables.API_URL + '/managers';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient,
              private _jwtHelper: JwtHelperService,
              private _errorResolver: ErrorResolverService) {
  }

  public getEmployees(): Observable<Array<Employee>> {
    return this._http
      .get<Array<Employee>>(`this.url/${this._jwtHelper.getSubjectId()}/employees`, {
        headers: this.headers,
      })
      .catch((error: any) => {
        this._errorResolver.createAlert(error);
        return Observable.of([]);
      });
  }

}
