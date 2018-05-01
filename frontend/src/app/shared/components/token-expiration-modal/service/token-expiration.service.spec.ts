import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MatButtonModule, MatDialogModule } from '@angular/material';

import { TokenExpirationModalComponent } from '@shared/components/token-expiration-modal/token-expiration-modal.component';
import { TokenExpirationService } from './token-expiration.service';

describe('TokenExpirationService', () => {
  let service: TokenExpirationService;
  let http: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TokenExpirationModalComponent],
      imports: [HttpClientTestingModule, MatDialogModule, MatButtonModule],
      providers: [TokenExpirationService],
    });
    service = TestBed.get(TokenExpirationService);
    http = TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
