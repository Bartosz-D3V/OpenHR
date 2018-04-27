import { TestBed } from '@angular/core/testing';
import { MatDialog, MatDialogModule } from '@angular/material';

import { TokenObserverService } from './token-observer.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';

describe('TokenObserverService', () => {
  let service: TokenObserverService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TokenObserverService, JwtHelperService, MatDialog],
      imports: [MatDialogModule],
    });
    service = TestBed.get(TokenObserverService);
  });

  beforeEach(() => {
    spyOn(service, 'observe');
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
