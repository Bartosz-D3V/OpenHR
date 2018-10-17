import { TestBed, inject } from '@angular/core/testing';

import { DelegationService } from './delegation.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { Injectable } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('DelegationService', () => {
  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {}
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [DelegationService, JwtHelperService, { provide: ErrorResolverService, useClass: FakeErrorResolverService }],
    });
  });

  it('should be created', inject([DelegationService], (service: DelegationService) => {
    expect(service).toBeTruthy();
  }));
});
