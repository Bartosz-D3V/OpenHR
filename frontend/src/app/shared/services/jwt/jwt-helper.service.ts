import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/timer';
import 'rxjs/add/operator/take';

import { SystemVariables } from '@config/system-variables';
import { Jwt } from '../../domain/auth/jwt';
import { Role } from '../../domain/subject/role';

@Injectable()
export class JwtHelperService {
  private readonly tokenPrefix: string = SystemVariables.TOKEN_PREFIX;
  private readonly refreshTokenPrefix: string = SystemVariables.REFRESH_TOKEN_PREFIX;

  public saveToken(token: string): void {
    window.localStorage.setItem(this.tokenPrefix, token);
  }

  public saveRefreshToken(token: string): void {
    window.localStorage.setItem(this.refreshTokenPrefix, token);
  }

  public getToken(): string {
    return window.localStorage.getItem(this.tokenPrefix);
  }

  public getRefreshToken(): string {
    return window.localStorage.getItem(this.refreshTokenPrefix);
  }

  public removeToken(): void {
    window.localStorage.removeItem(this.tokenPrefix);
  }

  public isTokenExpired(): boolean {
    const jwt: Jwt = this.parseToken(this.getToken());
    return jwt.exp - jwt.iat <= 0;
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

  public startIATObserver(token: string): Observable<number> {
    const jwt: Jwt = this.parseToken(token);
    const duration: number = jwt.exp - jwt.iat;
    return Observable.timer(1000, 1000)
      .map(i => duration - i)
      .take(duration + 1)
      .filter(i => i <= 500);
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
