import { TestBed, tick, fakeAsync } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { SystemVariables } from '@config/system-variables';
import { WorkersService } from '@modules/core/pages/workers/service/workers.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';
import { Employee } from '@shared/domain/subject/employee';

describe('WorkersService', () => {
  let http: HttpTestingController;
  let service: WorkersService;
  const employee1: Employee = new Employee(null, null, null, null, null);
  employee1.subjectId = 1;
  const employee2: Employee = new Employee(null, null, null, null, null);
  employee2.subjectId = 2;
  const mockEmployees: Array<Employee> = [employee1, employee2];

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [WorkersService, JwtHelperService],
      imports: [HttpClientTestingModule],
    });
    http = TestBed.get(HttpTestingController);
    service = TestBed.get(WorkersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('API access methods', () => {
    const apiLink: string = SystemVariables.API_URL + '/subjects';

    it('should query current service URL', fakeAsync(() => {
      service.getWorkers().subscribe();
      http.expectOne(`${apiLink}/lightweight`);
    }));

    describe('getEmployees', () => {
      it('should return an Observable of type Array of type employee', fakeAsync(() => {
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
      }));

      it('should resolve error if server is down', fakeAsync(() => {
        let result: Array<LightweightSubject>;
        let error: any;
        service.getWorkers().subscribe((res: Array<LightweightSubject>) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: `${apiLink}/lightweight`,
            method: 'GET',
          })
          .error(new ErrorEvent('404'));
        tick();

        expect(result).toBeUndefined();
        expect(error).toBeDefined();
        expect(error.message).toBe('Http failure response for /api/subjects/lightweight: 0 ');
      }));
    });
  });
});
