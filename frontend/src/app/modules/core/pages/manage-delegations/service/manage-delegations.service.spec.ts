import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { TestBed, inject } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ManageDelegationsService } from './manage-delegations.service';

describe('ManageDelegationsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, MatDialogModule, NoopAnimationsModule],
      providers: [JwtHelperService, ManageDelegationsService],
    });
  });

  it('should be created', inject([ManageDelegationsService], (service: ManageDelegationsService) => {
    expect(service).toBeTruthy();
  }));
});
