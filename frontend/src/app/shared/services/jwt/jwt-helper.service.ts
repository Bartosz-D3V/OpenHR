import { Injectable } from '@angular/core';
import { Jwt } from './jwt';

import { Moment } from 'moment';
import * as moment from 'moment';

@Injectable()
export class JwtHelperService {

  public isTokenExpired(token: string): boolean {
    const jwt: Jwt = this.parseToken(token);
    const expirationDate: Moment = moment.unix(jwt.exp);
    return expirationDate.isBefore(moment());
  }

  public getUsersRole(token: string): Array<string> {
    const jwt: Jwt = this.parseToken(token);
    return jwt.scopes;
  }

  public hasRole(token: string, role: string): boolean {
    const jwt: Jwt = this.parseToken(token);
    return jwt.scopes.indexOf(role) > -1;
  }

  public parseToken(token: string): Jwt {
    const base64Text: string = token.split('.')[1];
    const base64: string = base64Text.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64));
  }

}
