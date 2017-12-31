import { Injectable } from '@angular/core';
import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ErrorResolverService } from '../../../../../shared/services/error-resolver/error-resolver.service';
import { EmployeesService } from './employees.service';

describe('EmployeesService', () => {
  let http: HttpTestingController;
  let employeesService: EmployeesService;
  let errorResolverService: ErrorResolverService;

  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {
    }
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        EmployeesService,
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
      imports: [
        HttpClientTestingModule,
      ],
    });
    http = TestBed.get(HttpTestingController);
    employeesService = TestBed.get(EmployeesService);
    errorResolverService = TestBed.get(ErrorResolverService);
  });

  it('should be created', inject([EmployeesService], (service: EmployeesService) => {
    expect(service).toBeTruthy();
  }));

});
