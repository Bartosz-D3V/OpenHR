import {Injectable} from '@angular/core';
import {TestBed, inject} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';

import {ErrorResolverService} from '../../../services/error-resolver/error-resolver.service';
import {JwtHelperService} from '../../../services/jwt/jwt-helper.service';
import {DateRangeService} from './date-range.service';

describe('DateRangeService', () => {
  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {}

    public createAlert(error: any): void {}
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        JwtHelperService,
        DateRangeService,
        {
          provide: ErrorResolverService,
          useClass: FakeErrorResolverService,
        },
      ],
    });
  });

  it(
    'should be created',
    inject([DateRangeService], (service: DateRangeService) => {
      expect(service).toBeTruthy();
    })
  );
});
