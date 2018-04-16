import { TestBed } from '@angular/core/testing';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { HrTeamMemberService } from './hr-team-member.service';

describe('HrTeamMemberService', () => {
  let service: HrTeamMemberService;
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HrTeamMemberService, JwtHelperService],
    });
    service = TestBed.get(HrTeamMemberService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
