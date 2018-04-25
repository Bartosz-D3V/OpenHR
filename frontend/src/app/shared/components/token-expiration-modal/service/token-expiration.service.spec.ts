import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TokenExpirationService } from './token-expiration.service';

describe('TokenExpirationService', () => {
  let service: TokenExpirationService;
  let http: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TokenExpirationService],
      imports: [HttpClientTestingModule],
    });
    service = TestBed.get(TokenExpirationService);
    http = TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
