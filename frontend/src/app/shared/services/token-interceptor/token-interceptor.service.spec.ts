import { TestBed, inject } from '@angular/core/testing';

import { TokenInterceptorService } from './token-interceptor.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';

describe('TokenInterceptorService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TokenInterceptorService, JwtHelperService],
    });
  });

  it(
    'should be created',
    inject([TokenInterceptorService], (service: TokenInterceptorService) => {
      expect(service).toBeTruthy();
    })
  );
});
