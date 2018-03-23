import { TestBed } from '@angular/core/testing';

import { ManagerGuard } from './manager.guard';
import { JwtHelperService } from '../../services/jwt/jwt-helper.service';

describe('ManagerGuard', () => {
  let guard: ManagerGuard;
  let service: JwtHelperService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ManagerGuard, JwtHelperService],
    });
    guard = TestBed.get(ManagerGuard);
    service = TestBed.get(JwtHelperService);
  });

  it('should create', () => {
    expect(guard).toBeTruthy();
  });

  describe('canActivate method', () => {
    it('should return false if token is expired', () => {
      spyOn(guard['_jwtHelper'], 'isTokenExpired').and.returnValue(true);

      expect(guard.canActivate()).toBeFalsy();
    });

    it('should return false if user has no ROLE_MANAGER', () => {
      spyOn(guard['_jwtHelper'], 'hasRole').and.returnValue(false);

      expect(guard.canActivate()).toBeFalsy();
    });

    it('should return true if user has ROLE_MANAGER and token is valid', () => {
      spyOn(guard['_jwtHelper'], 'hasRole').and.returnValue(true);
      spyOn(guard['_jwtHelper'], 'isTokenExpired').and.returnValue(false);

      expect(guard.canActivate()).toBeTruthy();
    });
  });
});
