import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { DateRangeService } from './date-range.service';

describe('DateRangeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [JwtHelperService, DateRangeService],
    });
  });

  it('should be created', inject([DateRangeService], (service: DateRangeService) => {
    expect(service).toBeTruthy();
  }));
});
