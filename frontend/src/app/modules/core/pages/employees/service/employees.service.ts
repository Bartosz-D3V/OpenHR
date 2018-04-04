import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { ErrorResolverService } from '@shared//services/error-resolver/error-resolver.service';
import { Employee } from '@shared//domain/subject/employee';

@Injectable()
export class EmployeesService {
  private url: string = SystemVariables.API_URL + 'manager/employees';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
  });

  constructor(private _http: HttpClient, private _errorResolver: ErrorResolverService) {}

  public getEmployees(): Observable<Array<Employee>> {
    return this._http
      .get<Array<Employee>>(this.url, {
        headers: this.headers,
      })
      .catch((error: any) => {
        this._errorResolver.handleError(error);
        return Observable.of([]);
      });
  }
}
