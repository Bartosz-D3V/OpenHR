import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { HrTeamMemberService } from './hr-team-member.service';

describe('HrTeamMemberService', () => {
  let service: HrTeamMemberService;
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HrTeamMemberService, JwtHelperService],
      imports: [HttpClientTestingModule],
    });
    service = TestBed.get(HrTeamMemberService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
