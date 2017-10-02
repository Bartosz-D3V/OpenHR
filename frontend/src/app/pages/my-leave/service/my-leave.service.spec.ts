import { Injectable, ReflectiveInjector } from '@angular/core';
import {
  BaseRequestOptions, ConnectionBackend, Http, HttpModule, RequestOptions,
  ResponseOptions
} from '@angular/http';

import { TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';

import { ErrorResolverService } from '../../../shared/services/error-resolver/error-resolver.service';
import { MyLeaveService } from './my-leave.service';

@Injectable()
class FakeErrorResolverService {
  public createAlert(error: any): void {
  }
}

describe('MyLeaveService', () => {
  const mockLeaveTypes: Array<string> = ['Holiday', 'Maternity leave'];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpModule,
      ],
      providers: [
        MyLeaveService,
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
      MyLeaveService,
    ]);
    this.myLeaveService = this.injector.get(MyLeaveService);
    this.backend = this.injector.get(ConnectionBackend) as MockBackend;
    this.backend.connections.subscribe((connection: any) => this.lastConnection = connection);

    spyOn(console, 'log');
  });

  it('should be created', inject([MyLeaveService], (service: MyLeaveService) => {
    expect(service).toBeTruthy();
  }));

  describe('handleError method', () => {
    const mockError = 'Mock Error';

    it('should log actual error', inject([MyLeaveService], (service: MyLeaveService) => {
      service['handleError'](mockError);

      expect(console.log).toHaveBeenCalledTimes(1);
      expect(console.log).toHaveBeenCalledWith('An error occurred', mockError);
    }));

    it('should trigger createAlert method', inject([MyLeaveService, ErrorResolverService],
      (service: MyLeaveService, errorResolver: ErrorResolverService) => {
        spyOn(errorResolver, 'createAlert');
        service['handleError'](mockError);

        expect(errorResolver.createAlert).toHaveBeenCalledTimes(1);
        expect(errorResolver.createAlert).toHaveBeenCalledWith(mockError);
      }));

  });

  describe('API access methods', () => {

    it('should query current service URL', fakeAsync(() => {
      this.myLeaveService.getLeaveTypes();

      expect(this.lastConnection.request.url).toMatch(/app\/my-leave/);
    }));

    describe('getLeaveTypes', () => {

      it('should return an Observable of type Array of type string', fakeAsync(() => {
        let result: any;
        let error: any;
        this.myLeaveService.getLeaveTypes()
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
        spyOn(this.myLeaveService, 'handleError');
        let result: Object;
        let error: any;
        this.myLeaveService.getLeaveTypes()
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
