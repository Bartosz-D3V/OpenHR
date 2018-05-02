import { TestBed } from '@angular/core/testing';

import { SystemVariables } from '@config/system-variables';
import { Jwt } from '../../domain/auth/jwt';
import { Role } from '../../domain/subject/role';
import { JwtHelperService } from './jwt-helper.service';

describe('JwtHelperService', () => {
  let service: JwtHelperService;
  const mockTokenString = `
  eyJhbGciOiJIUzUxMiJ9.
  eyJzdWJqZWN0SWQiOiIxM
  iIsInN1YiI6IkV4YW1wbG
  UiLCJzY29wZXMiOlsiUk9
  MRV9FTVBMT1lFRSJdLCJp
  YXQiOjE1MTcwMDgyMzAsI
  mV4cCI6MTUxNzAwODIzMX
  0.D_1ZaeIY-w9UZOfEKKW
  exHx0YnwK1vMmAotQczppG10
  `;
  const emptyJwt: Jwt = {
    sub: '',
    subjectId: 0,
    scopes: null,
    iat: 0,
    exp: 0,
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [JwtHelperService],
    });
    service = TestBed.get(JwtHelperService);
    service.removeToken();
    service.saveToken(mockTokenString);
    service.saveRefreshToken(mockTokenString);
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

  it('getRefreshToken should return token as a string', () => {
    expect(service.getRefreshToken()).toEqual(mockTokenString);
  });

  it('saveRefreshToken should save token into localStorage', () => {
    service.saveRefreshToken(mockTokenString);

    expect(window.localStorage.getItem(SystemVariables.REFRESH_TOKEN_PREFIX)).toEqual(mockTokenString);
  });

  it('removeToken should remove token from localStorage', () => {
    service.removeToken();

    expect(window.localStorage.getItem(SystemVariables.TOKEN_PREFIX)).toBeNull();
  });

  describe('parseToken', () => {
    it('should return empty object if validateToken method returned object', () => {
      spyOn(service, 'validateToken').and.returnValue(emptyJwt);

      expect(service.parseToken('')).toEqual(emptyJwt);
    });

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
      expect(parsedJwt.scopes).toEqual([Role.EMPLOYEE]);
      expect(parsedJwt.iat).toEqual(1517008230);
      expect(parsedJwt.exp).toEqual(1517008231);
    });
  });

  describe('getUsersRole', () => {
    it('should return users roles', () => {
      const roles: Array<string> = service.getUsersRole();

      expect(roles).toBeDefined();
      expect(roles).toEqual([Role.EMPLOYEE]);
    });
  });

  describe('isTokenExpired', () => {
    it('should return true if value exp within token is in the past', () => {
      spyOn(service, 'parseToken').and.returnValue({
        iat: 2,
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
      const hasRole: boolean = service.hasRole(Role.EMPLOYEE);

      expect(hasRole).toBeTruthy();
    });

    it('should return false if user does not have appropriate role', () => {
      const hasRole: boolean = service.hasRole(Role.HRTEAMMEMBER);

      expect(hasRole).toBeFalsy();
    });
  });

  describe('getSubjectId', () => {
    it('should return subject id', () => {
      const subjectId: number = service.getSubjectId();

      expect(subjectId).toEqual(12);
    });

    it('should return 0 if token is invalid', () => {
      spyOn(service, 'parseToken').and.returnValue({});
      const subjectId: number = service.getSubjectId();

      expect(subjectId).toEqual(0);
    });
  });

  describe('validateToken', () => {
    it('should return empty token object if passed token is null', () => {
      expect(service.parseToken('')).toEqual(emptyJwt);
    });

    it('should return empty token object if passed token is less than 10 chars in length', () => {
      expect(service.parseToken('x'.repeat(9))).toEqual(emptyJwt);
    });

    it('should return empty token object if passed token does not have two dots', () => {
      expect(service.parseToken(mockTokenString.replace('.', ''))).toEqual(emptyJwt);
    });
  });
});
