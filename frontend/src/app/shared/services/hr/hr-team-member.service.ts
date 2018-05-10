import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { HrTeamMember } from '@shared/domain/subject/hr-team-member';
import { Employee } from '@shared/domain/subject/employee';

@Injectable()
export class HrTeamMemberService {
  private url: string = SystemVariables.API_URL + '/hr-team-members';
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
  });

  constructor(private _http: HttpClient) {}

  public getHrTeamMembers(): Observable<Array<HrTeamMember>> {
    return this._http.get<Array<HrTeamMember>>(this.url, {
      headers: this.headers,
    });
  }

  public getHrTeamMember(subjectId: number): Observable<HrTeamMember> {
    return this._http.get<HrTeamMember>(`${this.url}/${subjectId}`, {
      headers: this.headers,
    });
  }

  public createHrTeamMember(hrTeamMember: HrTeamMember): Observable<HrTeamMember> {
    return this._http.post<Employee>(this.url, hrTeamMember, {
      headers: this.headers,
    });
  }

  public updateHrTeamMember(hrTeamMember: HrTeamMember): Observable<HrTeamMember> {
    return this._http.put<Employee>(`${this.url}/${hrTeamMember.subjectId}`, hrTeamMember, {
      headers: this.headers,
    });
  }

  public deleteHrTeamMember(subjectId: number): Observable<null> {
    return this._http.delete<null>(`${this.url}/${subjectId}`);
  }
}
