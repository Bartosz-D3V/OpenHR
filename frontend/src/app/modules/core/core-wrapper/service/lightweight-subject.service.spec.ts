import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { MatDialogModule } from '@angular/material';

import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
import { JwtHelperService } from '../../../../shared/services/jwt/jwt-helper.service';
import { LightweightSubjectService } from './lightweight-subject.service';

describe('LightweightSubjectService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        LightweightSubjectService,
        JwtHelperService,
        ErrorResolverService,
      ],
      imports: [
        HttpClientTestingModule,
        MatDialogModule,
      ],
    });
  });

  it('should be created', inject([LightweightSubjectService], (service: LightweightSubjectService) => {
    expect(service).toBeTruthy();
  }));
});
