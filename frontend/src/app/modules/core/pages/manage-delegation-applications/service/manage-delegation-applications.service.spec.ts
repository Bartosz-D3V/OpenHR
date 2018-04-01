import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { TestBed, inject } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ManageDelegationApplicationsService } from './manage-delegation-applications.service';

describe('ManageDelegationApplicationsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, MatDialogModule, NoopAnimationsModule],
      providers: [JwtHelperService, ManageDelegationApplicationsService],
    });
  });

  it(
    'should be created',
    inject([ManageDelegationApplicationsService], (service: ManageDelegationApplicationsService) => {
      expect(service).toBeTruthy();
    })
  );
});
