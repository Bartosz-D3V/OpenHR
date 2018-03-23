import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { MainGuard } from './main.guard';
import { JwtHelperService } from '../../services/jwt/jwt-helper.service';

describe('MainGuard', () => {
  let guard: MainGuard;
  let service: JwtHelperService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MainGuard, JwtHelperService],
      imports: [RouterTestingModule],
    });
    guard = TestBed.get(MainGuard);
    service = TestBed.get(JwtHelperService);

    spyOn(guard['_router'], 'navigate');
  });

  it('should create', () => {
    expect(guard).toBeTruthy();
  });

  describe('canActivate method', () => {
    it('should return false if token is expired', () => {
      spyOn(guard['_jwtHelper'], 'isTokenExpired').and.returnValue(true);

      expect(guard.canActivate()).toBeFalsy();
    });

    it('should return false if user has no ROLE_MEMBER', () => {
      spyOn(guard['_jwtHelper'], 'hasRole').and.returnValue(false);

      expect(guard.canActivate()).toBeFalsy();
    });

    it('should navigate to login page if the returned value is false', () => {
      spyOn(guard['_jwtHelper'], 'hasRole').and.returnValue(false);
      guard.canActivate();

      expect(guard['_router'].navigate).toHaveBeenCalled();
      expect(guard['_router'].navigate).toHaveBeenCalledWith(['/login']);
    });

    it('should return true if user has ROLE_MEMBER and token is valid', () => {
      spyOn(guard['_jwtHelper'], 'hasRole').and.returnValue(true);
      spyOn(guard['_jwtHelper'], 'isTokenExpired').and.returnValue(false);

      expect(guard.canActivate()).toBeTruthy();
    });
  });
});
