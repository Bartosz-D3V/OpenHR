import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { Subject } from '@shared/domain/subject/subject';
import { MonthSummary } from '@modules/core/pages/dashboard/domain/month-summary';
import { ApplicationsStatusRadio } from '@modules/core/pages/dashboard/domain/applications-status-radio';
import { TotalExpenditure } from '@modules/core/pages/dashboard/domain/total-expenditure';
import * as moment from 'moment';

@Injectable()
export class DashboardService {
  private url: string = SystemVariables.API_URL + '/dashboard';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
  });

  constructor(private _http: HttpClient, private _jwtHelper: JwtHelperService) {}

  public getMonthlyReport(): Observable<Array<MonthSummary>> {
    return this._http.get<Array<MonthSummary>>(`${this.url}/${this._jwtHelper.getSubjectId()}/monthly-report`, {
      headers: this.headers,
    });
  }

  public getApplicationsStatusRatio(): Observable<ApplicationsStatusRadio> {
    return this._http.get<ApplicationsStatusRadio>(`${this.url}/status-ratio`, {
      headers: this.headers,
    });
  }

  public getTotalDelegationExpenditures(): Observable<TotalExpenditure> {
    const params: HttpParams = new HttpParams().set(
      'year',
      moment()
        .year()
        .toString()
    );
    return this._http.get<TotalExpenditure>(`${this.url}/delegation-expenditures`, {
      params: params,
      headers: this.headers,
    });
  }

  public getSubjectsOnLeave(): Observable<Array<Subject>> {
    return this._http.get<Array<Subject>>(`${this.url}/on-leave-today`, {
      headers: this.headers,
    });
  }
}
