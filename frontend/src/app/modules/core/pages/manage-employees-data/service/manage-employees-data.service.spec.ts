import { Injectable } from '@angular/core';
import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { ErrorResolverService } from '../../../../../shared/services/error-resolver/error-resolver.service';
import { JwtHelperService } from '../../../../../shared/services/jwt/jwt-helper.service';
import { ManageEmployeesDataService } from './manage-employees-data.service';

describe('ManageEmployeesDataService', () => {
  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {
    }
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
      ],
      providers: [
        ManageEmployeesDataService,
        JwtHelperService,
        {provide: ErrorResolverService, useClass: FakeErrorResolverService},
      ],
    });
  });

  it('should be created', inject([ManageEmployeesDataService], (service: ManageEmployeesDataService) => {
    expect(service).toBeTruthy();
  }));
});
