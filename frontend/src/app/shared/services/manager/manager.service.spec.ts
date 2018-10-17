import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { JwtHelperService } from '../jwt/jwt-helper.service';
import { ManagerService } from './manager.service';

describe('ManagerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ManagerService, JwtHelperService],
    });
  });

  it('should be created', inject([ManagerService], (service: ManagerService) => {
    expect(service).toBeTruthy();
  }));
});
