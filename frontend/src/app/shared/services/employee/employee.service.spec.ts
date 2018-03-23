import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { JwtHelperService } from '../jwt/jwt-helper.service';
import { EmployeeService } from './employee.service';

describe('EmployeeService', () => {
  let http: HttpTestingController;
  let service: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [JwtHelperService, EmployeeService],
    });
    http = TestBed.get(HttpTestingController);
    service = TestBed.get(EmployeeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
