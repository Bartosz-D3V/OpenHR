import { Injectable, ReflectiveInjector } from '@angular/core';
import {
  BaseRequestOptions, ConnectionBackend, Http, HttpModule, RequestOptions,
  ResponseOptions
} from '@angular/http';

import { TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';

import { ErrorResolverService } from '../../../shared/services/error-resolver/error-resolver.service';
import { LeaveApplicationService } from './leave-application.service';

describe('LeaveApplicationService', () => {
  const mockLeaveTypes: Array<string> = ['Holiday', 'Maternity leave'];

  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {
    }
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpModule,
      ],
      providers: [
        LeaveApplicationService,
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
    });
  });

  beforeEach(() => {
    this.injector = ReflectiveInjector.resolveAndCreate([
      {
        provide: ConnectionBackend, useClass: MockBackend
      },
      {
        provide: RequestOptions, useClass: BaseRequestOptions
      },
      {
        provide: ErrorResolverService, useClass: FakeErrorResolverService,
      },
      Http,
      LeaveApplicationService,
    ]);
    this.LeaveApplicationService = this.injector.get(LeaveApplicationService);
    this.backend = this.injector.get(ConnectionBackend) as MockBackend;
    this.backend.connections.subscribe((connection: any) => this.lastConnection = connection);

    spyOn(console, 'log');
  });

  it('should be created', inject([LeaveApplicationService], (service: LeaveApplicationService) => {
    expect(service).toBeTruthy();
  }));

  describe('handleError method', () => {
    const mockError = 'Mock Error';

    it('should log actual error', inject([LeaveApplicationService], (service: LeaveApplicationService) => {
      service['handleError'](mockError);

      expect(console.log).toHaveBeenCalledTimes(1);
      expect(console.log).toHaveBeenCalledWith('An error occurred', mockError);
    }));

    it('should trigger createAlert method', inject([LeaveApplicationService, ErrorResolverService],
      (service: LeaveApplicationService, errorResolver: ErrorResolverService) => {
        spyOn(errorResolver, 'createAlert');
        service['handleError'](mockError);

        expect(errorResolver.createAlert).toHaveBeenCalledTimes(1);
        expect(errorResolver.createAlert).toHaveBeenCalledWith(mockError);
      }));

  });

  describe('API access methods', () => {

    it('should query current service URL', fakeAsync(() => {
      this.LeaveApplicationService.getLeaveTypes();

      expect(this.lastConnection.request.url).toMatch(/app\/leave-application/);
    }));

    describe('getLeaveTypes', () => {

      it('should return an Observable of type Array of type string', fakeAsync(() => {
        let result: any;
        let error: any;
        this.LeaveApplicationService.getLeaveTypes()
          .subscribe(
            (res: Array<string>) => result = res,
            (err: any) => error = err);
        this.lastConnection.mockRespond(new Response(new ResponseOptions({
          body: JSON.stringify(mockLeaveTypes),
        })));
        tick();
        /**
         * tests goes here
         */
      }));

      it('should resolve error if server is down', fakeAsync(() => {
        spyOn(this.LeaveApplicationService, 'handleError');
        let result: Object;
        let error: any;
        this.LeaveApplicationService.getLeaveTypes()
          .subscribe(
            (res: Object) => result = res,
            (err: any) => error = err);
        this.lastConnection.mockError(new Response(new ResponseOptions({
          status: 404,
          statusText: 'URL not found',
        })));
        tick();
        /**
         * tests goes here
         */
      }));

    });

  });

});
