import { Injectable, ReflectiveInjector } from '@angular/core';
import { BaseRequestOptions, ConnectionBackend, Http, HttpModule, RequestOptions } from '@angular/http';

import { TestBed, inject, async, fakeAsync } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';

import { MyDetailsService } from './my-details.service';
import { ErrorResolverService } from '../../../shared/services/error-resolver/error-resolver.service';

@Injectable()
class FakeErrorResolverService {
  public createAlert(error: any): void {
  }
}

describe('MyDetailsService', () => {
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

    beforeEach(() => {
      spyOn(console, 'log');
    });

    it('should query current service URL', fakeAsync(() => {
      this.myDetailsService.getCurrentSubject();

      expect(this.lastConnection.request.url).toMatch(/app\/my-details/);
    }));

  });

});
