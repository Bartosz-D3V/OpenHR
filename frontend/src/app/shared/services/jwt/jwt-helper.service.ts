import {Injectable} from '@angular/core';
import {Moment} from 'moment';
import * as moment from 'moment';

import {SystemVariables} from '@config/system-variables';
import {Jwt} from '../../domain/auth/jwt';
import {Role} from '../../domain/subject/role';

@Injectable()
export class JwtHelperService {
  private readonly tokenPrefix: string = SystemVariables.TOKEN_PREFIX;

  public saveToken(token: string): void {
    window.localStorage.setItem(this.tokenPrefix, token);
  }

  public getToken(): string {
    return window.localStorage.getItem(this.tokenPrefix);
  }

  public removeToken(): void {
    window.localStorage.removeItem(this.tokenPrefix);
  }

  public isTokenExpired(): boolean {
    const jwt: Jwt = this.parseToken(this.getToken());
    const expirationDate: Moment = moment.unix(jwt.exp);
    return expirationDate.isBefore(moment());
  }

  public getUsersRole(): Array<Role> {
    const jwt: Jwt = this.parseToken(this.getToken());
    return jwt.scopes;
  }

  public hasRole(role: Role): boolean {
    const jwt: Jwt = this.parseToken(this.getToken());
    return jwt.scopes.includes(role);
  }

  public getSubjectId(): number {
    const jwt: Jwt = this.parseToken(this.getToken());
    return Number(jwt.subjectId) || 0;
  }

  public parseToken(token: string): Jwt {
    const invalidToken = this.validateToken(token);
    if (invalidToken !== undefined && 'scopes' in invalidToken) {
      return invalidToken;
    }
    const base64Text: string = token.split('.')[1];
    const base64: string = base64Text.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64));
  }

  public validateToken(token: string): Jwt {
    const emptyJwt: Jwt = {
      sub: '',
      scopes: null,
      subjectId: 0,
      iat: 0,
      exp: 0,
    };
    if (token === null || token.length < 10) {
      return emptyJwt;
    }
    if (token.split('.').length - 1 !== 2) {
      return emptyJwt;
    }
  }
}
