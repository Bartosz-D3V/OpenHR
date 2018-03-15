import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { Country } from '@shared/domain/country/country';

@Injectable()
export class DelegationService {
  private readonly url: string = SystemVariables.API_URL + '/delegations';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient,
              private _jwtHelper: JwtHelperService) {
  }

  public getCountries(): Observable<Array<Country>> {
    return this._http
      .get<Array<Country>>(`${this.url}/countries`, {
        headers: this.headers,
      });
  }
}
