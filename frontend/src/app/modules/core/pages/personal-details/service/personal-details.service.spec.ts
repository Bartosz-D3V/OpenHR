import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatDialogModule } from '@angular/material';

import { JwtHelperService } from '@shared//services/jwt/jwt-helper.service';
import { ErrorResolverService } from '@shared//services/error-resolver/error-resolver.service';
import { PersonalDetailsService } from './personal-details.service';

describe('PersonalDetailsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        MatDialogModule,
      ],
      providers: [
        JwtHelperService,
        ErrorResolverService,
        PersonalDetailsService,
      ],
    });
  });

  it('should be created', inject([PersonalDetailsService], (service: PersonalDetailsService) => {
    expect(service).toBeTruthy();
  }));
});
