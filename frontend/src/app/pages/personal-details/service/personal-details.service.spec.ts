import { Injectable, ReflectiveInjector } from '@angular/core';
import {
  BaseRequestOptions, ConnectionBackend, Http, HttpModule, RequestOptions,
  ResponseOptions, Response
} from '@angular/http';

import { TestBed, inject, async, fakeAsync, tick } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';

import { Subject } from '../../../shared/domain/subject/subject';
import { Address } from '../../../shared/domain/subject/address';
import { PersonalDetailsService } from './personal-details.service';
import { ErrorResolverService } from '../../../shared/services/error-resolver/error-resolver.service';

describe('PersonalDetailsService', () => {
  const mockSubject = new Subject('John', 'Test', new Date(1, 2, 1950),
    'Mentor', '12345678', 'test@test.com', new Address('', '', '', '', '', ''));

  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpModule,
      ],
      providers: [
        PersonalDetailsService,
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
      PersonalDetailsService,
    ]);
    this.PersonalDetailsService = this.injector.get(PersonalDetailsService);
    this.backend = this.injector.get(ConnectionBackend) as MockBackend;
    this.backend.connections.subscribe((connection: any) => this.lastConnection = connection);

    spyOn(console, 'log');
  });

  it('should be created', inject([PersonalDetailsService], (service: PersonalDetailsService) => {
    expect(service).toBeTruthy();
  }));

  describe('handleError method', () => {
    const mockError = 'Mock Error';

    it('should log actual error', inject([PersonalDetailsService], (service: PersonalDetailsService) => {
      service['handleError'](mockError);

      expect(console.log).toHaveBeenCalledTimes(1);
      expect(console.log).toHaveBeenCalledWith('An error occurred', mockError);
    }));

    it('should trigger createAlert method', inject([PersonalDetailsService, ErrorResolverService],
      (service: PersonalDetailsService, errorResolver: ErrorResolverService) => {
        spyOn(errorResolver, 'createAlert');
        service['handleError'](mockError);

        expect(errorResolver.createAlert).toHaveBeenCalledTimes(1);
        expect(errorResolver.createAlert).toHaveBeenCalledWith(mockError);
      }));

  });

  describe('API access method', () => {

    it('should query current service URL', fakeAsync(() => {
      this.PersonalDetailsService.getCurrentSubject();

      expect(this.lastConnection.request.url).toMatch(/app\/my-details/);
    }));

    describe('getCurrentSubject', () => {

      it('should return an Observable of type Subject', fakeAsync(() => {
        let result: Object;
        let error: any;
        this.PersonalDetailsService.getCurrentSubject()
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
        spyOn(this.PersonalDetailsService, 'handleError');
        let result: Object;
        let error: any;
        this.PersonalDetailsService.getCurrentSubject()
          .subscribe(
            (res: Object) => result = res,
            (err: any) => error = err);
        this.lastConnection.mockError(new Response(new ResponseOptions({
          status: 404,
          statusText: 'URL not found',
        })));
        tick();

        expect(this.PersonalDetailsService['handleError']).toHaveBeenCalled();
      }));

    });

  });

});
