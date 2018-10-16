import { TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { LeaveApplicationService } from './leave-application.service';
import { JwtHelperService } from '@shared//services/jwt/jwt-helper.service';
import { SystemVariables } from '@config/system-variables';
import { LeaveType } from '@shared//domain/application/leave-type';

describe('LeaveApplicationService', () => {
  const mockLeaveTypes: Array<string> = ['Holiday', 'Maternity leave'];
  let http: HttpTestingController;
  let leaveApplicationService: LeaveApplicationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [JwtHelperService, LeaveApplicationService],
    });
    http = TestBed.get(HttpTestingController);
    leaveApplicationService = TestBed.get(LeaveApplicationService);
  });

  it('should be created', () => {
    expect(leaveApplicationService).toBeTruthy();
  });

  describe('API access methods', () => {
    const apiLink: string = SystemVariables.API_URL + '/leave-applications';

    it('should query current service URL', fakeAsync(() => {
      leaveApplicationService.getLeaveTypes().subscribe();
      http.expectOne(`${apiLink}/types`);
    }));

    describe('getLeaveTypes', () => {
      it('should return an Observable of type Array of type LeaveType', fakeAsync(() => {
        let result: any;
        let error: any;
        leaveApplicationService.getLeaveTypes().subscribe((res: Array<LeaveType>) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: `${apiLink}/types`,
            method: 'GET',
          })
          .flush(mockLeaveTypes);

        tick();
        /**
         * tests goes here
         */
      }));

      it('should resolve error if server is down', fakeAsync(() => {
        let result: Object;
        let error: any;
        leaveApplicationService.getLeaveTypes().subscribe((res: Object) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: `${apiLink}/types`,
            method: 'GET',
          })
          .error(new ErrorEvent('404'));
        tick();
        /**
         * tests goes here
         */
      }));
    });
  });
});
