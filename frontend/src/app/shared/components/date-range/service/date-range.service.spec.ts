import { TestBed, inject } from '@angular/core/testing';

import { DateRangeService } from './date-range.service';
import { ErrorResolverService } from '../../../services/error-resolver/error-resolver.service';
import { Injectable } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('DateRangeService', () => {
  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {
    }

    public createAlert(error: any): void {
    }
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
      ],
      providers: [
        DateRangeService,
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
    });
  });

  it('should be created', inject([DateRangeService], (service: DateRangeService) => {
    expect(service).toBeTruthy();
  }));
});
