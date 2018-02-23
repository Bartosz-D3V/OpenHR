import { Injectable } from '@angular/core';
import { JwtHelperService } from '../jwt/jwt-helper.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SystemVariables } from '../../../config/system-variables';
import { Observable } from 'rxjs/Observable';
import { Manager } from '../../domain/subject/manager';

@Injectable()
export class ManagerService {
  private url: string = SystemVariables.API_URL + '/managers';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient,
              private _jwtHelper: JwtHelperService) {
  }

  public getManagers(): Observable<Array<Manager>> {
    return this._http
      .get<Array<Manager>>(this.url, {
        headers: this.headers,
      });
  }

}
