import { TestBed } from '@angular/core/testing';

import { JwtHelperService } from './jwt-helper.service';
import { Jwt } from './jwt';

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
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
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
      const roles: Array<string> = service.getUsersRole(mockTokenString);

      expect(roles).toBeDefined();
      expect(roles).toEqual(['ROLE_MEMBER']);
    });
  });

  describe('isTokenExpired', () => {
    it('should return true if value exp within token is in the past', () => {
      spyOn(service, 'parseToken').and.returnValue({
        exp: 0,
      });
      const isExpired: boolean = service.isTokenExpired(mockTokenString);

      expect(isExpired).toBeTruthy();
    });

    it('should return false if value exp within token is not in the past', () => {
      spyOn(service, 'parseToken').and.returnValue({
        exp: Number.MAX_SAFE_INTEGER,
      });
      const isExpired: boolean = service.isTokenExpired(mockTokenString);

      expect(isExpired).toBeFalsy();
    });
  });

  describe('hasRole', () => {
    it('should return true if user has appropriate role', () => {
      const hasRole: boolean = service.hasRole(mockTokenString, 'ROLE_MEMBER');

      expect(hasRole).toBeTruthy();
    });

    it('should return false if user does not have appropriate role', () => {
      const hasRole: boolean = service.hasRole(mockTokenString, 'ROLE_ADMIN');

      expect(hasRole).toBeFalsy();
    });
  });
});
