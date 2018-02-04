import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
import { LightweightSubjectService } from './lightweight-subject.service';
import { MatDialogModule } from '@angular/material';

describe('LightweightSubjectService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        LightweightSubjectService,
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
