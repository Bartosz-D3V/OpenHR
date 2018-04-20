import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { BankHolidayEngland } from '../domain/bank-holiday/england/bank-holiday-england';

@Injectable()
export class DateRangeService {
  private url: string = SystemVariables.API_URL + '/bank-holidays';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
  });

  constructor(private _jwtHelper: JwtHelperService, private _http: HttpClient) {}

  public getBankHolidaysInEnglandAndWales(): Observable<BankHolidayEngland> {
    return this._http.get<BankHolidayEngland>(`${this.url}/england`, {
      headers: this.headers,
    });
  }
}
