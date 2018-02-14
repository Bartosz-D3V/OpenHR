import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatDialogModule } from '@angular/material';

import { ErrorResolverService } from '../../../../../shared/services/error-resolver/error-resolver.service';
import { JwtHelperService } from '../../../../../shared/services/jwt/jwt-helper.service';
import { ManageLeaveApplicationsService } from './manage-leave-applications.service';

describe('ManageLeaveApplicationsService', () => {
  let service: ManageLeaveApplicationsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        MatDialogModule,
      ],
      providers: [
        ErrorResolverService,
        JwtHelperService,
        ManageLeaveApplicationsService,
      ],
    });
    service = TestBed.get(ManageLeaveApplicationsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
