import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { Observable } from 'rxjs/Observable';
import { MonthSummary } from '@modules/core/pages/dashboard/domain/month-summary';

@Injectable()
export class DashboardService {
  private url: string = SystemVariables.API_URL + '/dashboard';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient,
              private _jwtHelper: JwtHelperService) {
  }

  public getMonthlyReport(): Observable<Array<MonthSummary>> {
    return this._http
      .get<Array<MonthSummary>>(`${this.url}/${this._jwtHelper.getSubjectId()}/monthly-report`, {
        headers: this.headers,
      });
  }
}
