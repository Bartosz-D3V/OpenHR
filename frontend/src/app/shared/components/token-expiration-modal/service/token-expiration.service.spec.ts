import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TokenExpirationService } from './token-expiration.service';
import { TokenExpirationModalComponent } from '@shared/components/token-expiration-modal/token-expiration-modal.component';

describe('TokenExpirationService', () => {
  let service: TokenExpirationService;
  let http: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TokenExpirationModalComponent],
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
