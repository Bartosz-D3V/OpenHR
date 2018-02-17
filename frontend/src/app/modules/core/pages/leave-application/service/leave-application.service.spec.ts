import { Injectable } from '@angular/core';
import { TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { LeaveApplicationService } from './leave-application.service';
import { JwtHelperService } from '../../../../../shared/services/jwt/jwt-helper.service';
import { ErrorResolverService } from '../../../../../shared/services/error-resolver/error-resolver.service';
import { SystemVariables } from '../../../../../config/system-variables';
import { LeaveType } from '../../../../../shared/domain/leave-application/leave-type';

describe('LeaveApplicationService', () => {
  const mockLeaveTypes: Array<string> = ['Holiday', 'Maternity leave'];
  let http: HttpTestingController;
  let leaveApplicationService: LeaveApplicationService;
  let errorResolverService: ErrorResolverService;

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
        JwtHelperService,
        LeaveApplicationService,
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
    });
    http = TestBed.get(HttpTestingController);
    leaveApplicationService = TestBed.get(LeaveApplicationService);
    errorResolverService = TestBed.get(ErrorResolverService);
  });

  it('should be created', () => {
    expect(leaveApplicationService).toBeTruthy();
  });

  describe('handleError method', () => {
    const mockError = 'Mock Error';

    it('should trigger createAlert method', () => {
      spyOn(errorResolverService, 'createAlert');
      leaveApplicationService['handleError'](mockError);

      expect(errorResolverService.createAlert).toHaveBeenCalled();
      expect(errorResolverService.createAlert).toHaveBeenCalledWith(mockError);
    });

  });

  describe('API access methods', () => {
    const apiLink: string = SystemVariables.API_URL + '/leave-application';

    it('should query current service URL', fakeAsync(() => {
      leaveApplicationService.getLeaveTypes().subscribe();
      http.expectOne(`${apiLink}/types`);
    }));

    describe('getLeaveTypes', () => {

      it('should return an Observable of type Array of type LeaveType', fakeAsync(() => {
        let result: any;
        let error: any;
        leaveApplicationService.getLeaveTypes()
          .subscribe(
            (res: Array<LeaveType>) => result = res,
            (err: any) => error = err);
        http.expectOne({
          url: `${apiLink}/types`,
          method: 'GET',
        }).flush(mockLeaveTypes);

        tick();
        /**
         * tests goes here
         */
      }));

      it('should resolve error if server is down', fakeAsync(() => {
        let result: Object;
        let error: any;
        leaveApplicationService.getLeaveTypes()
          .subscribe(
            (res: Object) => result = res,
            (err: any) => error = err);
        http.expectOne({
          url: `${apiLink}/types`,
          method: 'GET',
        }).error(new ErrorEvent('404'));
        tick();
        /**
         * tests goes here
         */
      }));

    });

  });

});
