import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { AllowanceSettings } from '@modules/settings/pages/admin/domain/allowance-settings';

@Injectable()
export class AdminService {
  private readonly url: string = `${SystemVariables.API_URL}/admin-configuration`;
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
  });

  constructor(private _http: HttpClient) {}

  public getAdminAllowanceSettings(): Observable<AllowanceSettings> {
    return this._http.get<AllowanceSettings>(`${this.url}/allowance-settings`, {
      headers: this.headers,
    });
  }

  public updateAdminAllowanceSettings(allowanceSettings: AllowanceSettings): Observable<AllowanceSettings> {
    return this._http.put<AllowanceSettings>(`${this.url}/allowance-settings`, allowanceSettings, {
      headers: this.headers,
    });
  }
}
