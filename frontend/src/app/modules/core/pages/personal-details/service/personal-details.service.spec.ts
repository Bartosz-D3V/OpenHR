import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatDialogModule } from '@angular/material';

import { JwtHelperService } from '@shared//services/jwt/jwt-helper.service';
import { ErrorResolverService } from '@shared//services/error-resolver/error-resolver.service';
import { PersonalDetailsService } from './personal-details.service';
import { SystemVariables } from '@config/system-variables';

describe('PersonalDetailsService', () => {
  let service: PersonalDetailsService;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, MatDialogModule],
      providers: [JwtHelperService, ErrorResolverService, PersonalDetailsService],
    });
    service = TestBed.get(PersonalDetailsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('resolveUrl', () => {
    it('should return URL for employee', () => {
      expect(service.resolveUrl('EMPLOYEE')).toEqual(`${SystemVariables.API_URL}/employees`);
    });
    it('should return URL for manager', () => {
      expect(service.resolveUrl('MANAGER')).toEqual(`${SystemVariables.API_URL}/managers`);
    });
    it('should return URL for HR', () => {
      expect(service.resolveUrl('HRTEAMMEMBER')).toEqual(`${SystemVariables.API_URL}/hr-team-members`);
    });
  });
});
