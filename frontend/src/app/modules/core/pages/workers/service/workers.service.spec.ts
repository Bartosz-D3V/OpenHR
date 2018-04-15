import { Injectable } from '@angular/core';
import { TestBed, tick, fakeAsync } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { SystemVariables } from '@config/system-variables';
import { WorkersService } from '@modules/core/pages/workers/service/workers.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';
import { Employee } from '@shared/domain/subject/employee';

describe('EmployeesService', () => {
  let http: HttpTestingController;
  let service: WorkersService;
  let errorResolverService: ErrorResolverService;
  const employee1: Employee = new Employee(null, null, null, null, null);
  employee1.subjectId = 1;
  const employee2: Employee = new Employee(null, null, null, null, null);
  employee2.subjectId = 2;
  const mockEmployees: Array<Employee> = [employee1, employee2];

  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {}

    public createAlert(error: any): void {}
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        WorkersService,
        JwtHelperService,
        {
          provide: ErrorResolverService,
          useClass: FakeErrorResolverService,
        },
      ],
      imports: [HttpClientTestingModule],
    });
    http = TestBed.get(HttpTestingController);
    service = TestBed.get(WorkersService);
    errorResolverService = TestBed.get(ErrorResolverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('API access methods', () => {
    const apiLink: string = SystemVariables.API_URL + '/subjects';

    it(
      'should query current service URL',
      fakeAsync(() => {
        service.getWorkers().subscribe();
        http.expectOne(`${apiLink}/lightweight`);
      })
    );

    describe('getEmployees', () => {
      it(
        'should return an Observable of type Array of type employee',
        fakeAsync(() => {
          let result: Array<LightweightSubject>;
          let error: any;
          service.getWorkers().subscribe((res: Array<LightweightSubject>) => (result = res), (err: any) => (error = err));
          http
            .expectOne({
              url: `${apiLink}/lightweight`,
              method: 'GET',
            })
            .flush(mockEmployees);

          tick();

          expect(error).toBeUndefined();
          expect(result).toBeDefined();
          expect(result[0]).toBeDefined();
          expect(result[0].subjectId).toEqual(1);
          expect(result[1]).toBeDefined();
          expect(result[1].subjectId).toEqual(2);
        })
      );

      it(
        'should resolve error if server is down',
        fakeAsync(() => {
          let result: Object;
          let error: any;
          service.getWorkers().subscribe((res: Object) => (result = res), (err: any) => (error = err));
          http
            .expectOne({
              url: `${apiLink}/lightweight`,
              method: 'GET',
            })
            .error(new ErrorEvent('404'));
          tick();

          expect(result).toEqual([]);
          expect(error).toBeUndefined();
        })
      );
    });
  });
});
