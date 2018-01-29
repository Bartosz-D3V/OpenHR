import { TestBed } from '@angular/core/testing';

import { SystemVariables } from '../../../config/system-variables';
import { JwtHelperService } from './jwt-helper.service';
import { Jwt } from '../../domain/auth/jwt';

describe('JwtHelperService', () => {
  let service: JwtHelperService;
  const mockTokenString = `
  eyJhbGciOiJIUzUxMiJ9.
  eyJzdWIiOiJFeGFtcGxlIiwic2NvcGVzIjpbIlJPTEVfTUV
  NQkVSIl0sImlhdCI6MTUxNzAwODIzMCwiZXhwIjoxNTE3MDA4MjMxfQ.
  KaGUCKSC5M6QG61eZVQPcf31Db5LKWRvW90pikVlQ9M
  `;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        JwtHelperService,
      ],
    });
    service = TestBed.get(JwtHelperService);
    service.removeToken();
    service.saveToken(mockTokenString);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getToken should return token as a string', () => {
    expect(service.getToken()).toEqual(mockTokenString);
  });

  it('saveToken should save token into localStorage', () => {
    service.saveToken(mockTokenString);

    expect(window.localStorage.getItem(SystemVariables.TOKEN_PREFIX)).toEqual(mockTokenString);
  });

  it('removeToken should remove token from localStorage', () => {
    service.removeToken();

    expect(window.localStorage.getItem(SystemVariables.TOKEN_PREFIX)).toBeNull();
  });

  describe('parseToken', () => {
    it('should return object that implements JWT interface', () => {
      const parsedJwt: Object = service.parseToken(mockTokenString);

      expect(parsedJwt.hasOwnProperty('sub')).toBeTruthy();
      expect(parsedJwt.hasOwnProperty('scopes')).toBeTruthy();
      expect(parsedJwt.hasOwnProperty('iat')).toBeTruthy();
      expect(parsedJwt.hasOwnProperty('exp')).toBeTruthy();
    });

    it('should return JWT object with decoded values', () => {
      const parsedJwt: Jwt = service.parseToken(mockTokenString);

      expect(parsedJwt.sub).toEqual('Example');
      expect(parsedJwt.scopes).toEqual(['ROLE_MEMBER']);
      expect(parsedJwt.iat).toEqual(1517008230);
      expect(parsedJwt.exp).toEqual(1517008231);
    });
  });

  describe('getUsersRole', () => {
    it('should return users roles', () => {
      const roles: Array<string> = service.getUsersRole();

      expect(roles).toBeDefined();
      expect(roles).toEqual(['ROLE_MEMBER']);
    });
  });

  describe('isTokenExpired', () => {
    it('should return true if value exp within token is in the past', () => {
      spyOn(service, 'parseToken').and.returnValue({
        exp: 0,
      });
      const isExpired: boolean = service.isTokenExpired();

      expect(isExpired).toBeTruthy();
    });

    it('should return false if value exp within token is not in the past', () => {
      spyOn(service, 'parseToken').and.returnValue({
        exp: Number.MAX_SAFE_INTEGER,
      });
      const isExpired: boolean = service.isTokenExpired();

      expect(isExpired).toBeFalsy();
    });
  });

  describe('hasRole', () => {
    it('should return true if user has appropriate role', () => {
      const hasRole: boolean = service.hasRole('ROLE_MEMBER');

      expect(hasRole).toBeTruthy();
    });

    it('should return false if user does not have appropriate role', () => {
      const hasRole: boolean = service.hasRole('ROLE_ADMIN');

      expect(hasRole).toBeFalsy();
    });
  });
});
