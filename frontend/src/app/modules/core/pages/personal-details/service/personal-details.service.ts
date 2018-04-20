import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemVariables } from '@config/system-variables';
import { Subject } from '@shared//domain/subject/subject';
import { Role } from '@shared//domain/subject/role';

@Injectable()
export class PersonalDetailsService {
  private baseUrl: string = SystemVariables.API_URL;
  private readonly headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json',
  });

  constructor(private _http: HttpClient) {}

  public saveSubject(subject: Subject): Observable<Subject> {
    return this._http.put<Subject>(`${this.resolveUrl(subject.role)}/${subject.subjectId}`, subject, {
      headers: this.headers,
    });
  }

  public resolveUrl(subjectRole: Role | string): string {
    const url: string = this.baseUrl;
    switch (`ROLE_${subjectRole}`) {
      case Role.EMPLOYEE:
        return url.concat(`/employees`);
      case Role.MANAGER:
        return url.concat(`/managers`);
      case Role.HRTEAMMEMBER:
        return url.concat(`/hr-team-members`);
    }
  }
}
