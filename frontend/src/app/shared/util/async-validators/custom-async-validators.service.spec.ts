import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Observable } from 'rxjs/Observable';

import { AsyncValidatorService } from '@shared/util/async-validators/service/async-validator.service';
import { CustomAsyncValidatorsService } from './custom-async-validators.service';

describe('CustomAsyncValidatorsService', () => {
  @Injectable()
  class FakeAsyncValidatorService {
    public usernameIsFree(username: string): Observable<HttpResponse<null>> {
      return Observable.of(null);
    }
    public emailIsFree(email: string): Observable<HttpResponse<null>> {
      return Observable.of(null);
    }
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CustomAsyncValidatorsService, { provide: AsyncValidatorService, useClass: FakeAsyncValidatorService }],
    });
  });

  it('should be created', inject([CustomAsyncValidatorsService], (service: CustomAsyncValidatorsService) => {
    expect(service).toBeTruthy();
  }));
});
