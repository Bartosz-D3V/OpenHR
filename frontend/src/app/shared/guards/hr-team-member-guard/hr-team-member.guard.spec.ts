import { TestBed } from '@angular/core/testing';

import { HrTeamMemberGuard } from './hr-team-member.guard';
import { JwtHelperService } from '../../services/jwt/jwt-helper.service';

describe('HrTeamMemberGuard', () => {
  let guard: HrTeamMemberGuard;
  let service: JwtHelperService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HrTeamMemberGuard, JwtHelperService],
    });
    guard = TestBed.get(HrTeamMemberGuard);
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

    it('should return false if user has no ROLE_HRTEAMMEMBER', () => {
      spyOn(guard['_jwtHelper'], 'hasRole').and.returnValue(false);

      expect(guard.canActivate()).toBeFalsy();
    });

    it('should return true if user has ROLE_HRTEAMMEMBER and token is valid', () => {
      spyOn(guard['_jwtHelper'], 'hasRole').and.returnValue(true);
      spyOn(guard['_jwtHelper'], 'isTokenExpired').and.returnValue(false);

      expect(guard.canActivate()).toBeTruthy();
    });
  });
});
