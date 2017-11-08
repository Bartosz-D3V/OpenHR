import { Injectable, ReflectiveInjector } from '@angular/core';
import {
  BaseRequestOptions, ConnectionBackend, Http, HttpModule, RequestOptions,
  ResponseOptions
} from '@angular/http';

import { TestBed, inject, fakeAsync, tick } from '@angular/core/testing';

import { ErrorResolverService } from '../../../shared/services/error-resolver/error-resolver.service';
import { LeaveApplicationService } from './leave-application.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

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
        LeaveApplicationService,
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
    });
    http = TestBed.get(HttpTestingController);
    leaveApplicationService = TestBed.get(LeaveApplicationService);
    errorResolverService = TestBed.get(ErrorResolverService);

    spyOn(console, 'log');
  });

  it('should be created', () => {
    expect(leaveApplicationService).toBeTruthy();
  });

  describe('handleError method', () => {
    const mockError = 'Mock Error';

    it('should log actual error', () => {
      leaveApplicationService['handleError'](mockError);

      expect(console.log).toHaveBeenCalledTimes(1);
      expect(console.log).toHaveBeenCalledWith('An error occurred', mockError);
    });

    it('should trigger createAlert method', () => {
      spyOn(errorResolverService, 'createAlert');
      leaveApplicationService['handleError'](mockError);

      expect(errorResolverService.createAlert).toHaveBeenCalledTimes(1);
      expect(errorResolverService.createAlert).toHaveBeenCalledWith(mockError);
    });

  });

  describe('API access methods', () => {

    it('should query current service URL', fakeAsync(() => {
      leaveApplicationService.getLeaveTypes().subscribe();
      http.expectOne('app/leave-application');
    }));

    describe('getLeaveTypes', () => {

      it('should return an Observable of type Array of type string', fakeAsync(() => {
        let result: any;
        let error: any;
        leaveApplicationService.getLeaveTypes()
          .subscribe(
            (res: Array<string>) => result = res,
            (err: any) => error = err);
        http.expectOne({
          url: 'app/leave-application',
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
          url: 'app/leave-application',
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
