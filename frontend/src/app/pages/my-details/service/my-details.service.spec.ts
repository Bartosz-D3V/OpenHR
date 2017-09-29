import { Injectable, ReflectiveInjector } from '@angular/core';
import {
  BaseRequestOptions, ConnectionBackend, Http, HttpModule, RequestOptions,
  ResponseOptions, Response
} from '@angular/http';

import { TestBed, inject, async, fakeAsync, tick } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';

import { Subject } from '../classes/subject';
import { Address } from '../classes/address';
import { MyDetailsService } from './my-details.service';
import { ErrorResolverService } from '../../../shared/services/error-resolver/error-resolver.service';

@Injectable()
class FakeErrorResolverService {
  public createAlert(error: any): void {
  }
}

describe('MyDetailsService', () => {
  const mockSubject = new Subject('John', null, 'Test', new Date(1, 2, 1950),
    'Mentor', '12345678', 'test@test.com', new Address('', '', '', '', '', ''));

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpModule,
      ],
      providers: [
        MyDetailsService,
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
    });
  }));

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
      MyDetailsService,
    ]);
    this.myDetailsService = this.injector.get(MyDetailsService);
    this.backend = this.injector.get(ConnectionBackend) as MockBackend;
    this.backend.connections.subscribe((connection: any) => this.lastConnection = connection);
  });

  it('should be created', inject([MyDetailsService], (service: MyDetailsService) => {
    expect(service).toBeTruthy();
  }));

  describe('handleError method', () => {
    const mockError = 'Mock Error';

    it('should log actual error', inject([MyDetailsService], (service: MyDetailsService) => {
      spyOn(console, 'log');
      service['handleError'](mockError);

      expect(console.log).toHaveBeenCalledTimes(1);
      expect(console.log).toHaveBeenCalledWith('An error occurred', mockError);
    }));

    it('should trigger createAlert method', inject([MyDetailsService, ErrorResolverService],
      (service: MyDetailsService, errorResolver: ErrorResolverService) => {
        spyOn(errorResolver, 'createAlert');
        service['handleError'](mockError);

        expect(errorResolver.createAlert).toHaveBeenCalledTimes(1);
        expect(errorResolver.createAlert).toHaveBeenCalledWith(mockError);
      }));

  });

  describe('API access method', () => {

    it('should query current service URL', fakeAsync(() => {
      this.myDetailsService.getCurrentSubject();

      expect(this.lastConnection.request.url).toMatch(/app\/my-details/);
    }));

    describe('getCurrentSubject', () => {

      it('should return an Observable of type Subject', fakeAsync(() => {
        let result: Object;
        let error: any;
        this.myDetailsService.getCurrentSubject()
          .subscribe(
            (res: Object) => result = res,
            (err: any) => error = err);
        this.lastConnection.mockRespond(new Response(new ResponseOptions({
          body: JSON.stringify(mockSubject),
        })));
        tick();

        expect(error).toBeUndefined();
        expect(typeof result).toBe('object');
        expect(JSON.stringify(result)).toEqual(JSON.stringify(mockSubject));
      }));

      it('should resolve error if server is down', fakeAsync(() => {
        spyOn(this.myDetailsService, 'handleError');
        let result: Object;
        let error: any;
        this.myDetailsService.getCurrentSubject()
          .subscribe(
            (res: Object) => result = res,
            (err: any) => error = err);
        this.lastConnection.mockError(new Response(new ResponseOptions({
          status: 404,
          statusText: 'URL not found',
        })));
        tick();

        expect(this.myDetailsService['handleError']).toHaveBeenCalled();
      }));

    });

  });

});
