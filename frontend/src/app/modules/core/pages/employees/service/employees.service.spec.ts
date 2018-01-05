import { Injectable } from '@angular/core';
import { TestBed, inject, tick, fakeAsync } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ErrorResolverService } from '../../../../../shared/services/error-resolver/error-resolver.service';
import { EmployeesService } from './employees.service';
import { SystemVariables } from '../../../../../config/system-variables';
import { Employee } from '../domain/employee';

describe('EmployeesService', () => {
  let http: HttpTestingController;
  let service: EmployeesService;
  let errorResolverService: ErrorResolverService;
  const mockEmployees: Array<Employee> = [new Employee(1), new Employee(2)];

  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {
    }

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
    service = TestBed.get(EmployeesService);
    errorResolverService = TestBed.get(ErrorResolverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('API access methods', () => {
    const apiLink: string = SystemVariables.API_URL + 'manager/employees';

    it('should query current service URL', fakeAsync(() => {
      service.getEmployees().subscribe();
      http.expectOne(apiLink);
    }));

    describe('getEmployees', () => {

      it('should return an Observable of type Array of type employee', fakeAsync(() => {
        let result: Array<Employee>;
        let error: any;
        service.getEmployees()
          .subscribe(
            (res: Array<Employee>) => result = res,
            (err: any) => error = err);
        http.expectOne({
          url: apiLink,
          method: 'GET',
        }).flush(mockEmployees);

        tick();

        expect(error).toBeUndefined();
        expect(result).toBeDefined();
        expect(result[0]).toBeDefined();
        expect(result[0].employeeId).toEqual(1);
        expect(result[1]).toBeDefined();
        expect(result[1].employeeId).toEqual(2);
      }));

      it('should resolve error if server is down', fakeAsync(() => {
        spyOn(errorResolverService, 'handleError');
        let result: Object;
        let error: any;
        service.getEmployees()
          .subscribe(
            (res: Object) => result = res,
            (err: any) => error = err);
        http.expectOne({
          url: apiLink,
          method: 'GET',
        }).error(new ErrorEvent('404'));
        tick();

        expect(errorResolverService.handleError).toHaveBeenCalled();
        expect(result).toEqual([]);
        expect(error).toBeUndefined();
      }));
    });

  });

});