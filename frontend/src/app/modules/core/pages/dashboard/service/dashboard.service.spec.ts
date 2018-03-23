import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { DashboardService } from './dashboard.service';

describe('DashboardService', () => {
  let service: DashboardService;
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DashboardService, JwtHelperService],
      imports: [HttpClientTestingModule],
    });
    service = TestBed.get(DashboardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
