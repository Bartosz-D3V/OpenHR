import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SystemVariables } from '@config/system-variables';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { HrTeamMember } from '@shared/domain/subject/hr-team-member';
import { Observable } from 'rxjs/Observable';
import { Employee } from '@shared/domain/subject/employee';

@Injectable()
export class HrTeamMemberService {
  private url: string = SystemVariables.API_URL + '/hr-team-members';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
    Authorization: 'Bearer-' + this._jwtHelper.getToken(),
  });

  constructor(private _http: HttpClient, private _jwtHelper: JwtHelperService) {}

  public createHrTeamMember(hrTeamMember: HrTeamMember): Observable<HrTeamMember> {
    return this._http.post<Employee>(this.url, hrTeamMember, {
      headers: this.headers,
    });
  }
}
