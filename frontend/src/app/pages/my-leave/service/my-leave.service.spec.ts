import { Injectable, ReflectiveInjector } from '@angular/core';
import { BaseRequestOptions, ConnectionBackend, Http, HttpModule, RequestOptions } from '@angular/http';

import { TestBed, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';

import { ErrorResolverService } from '../../../shared/services/error-resolver/error-resolver.service';
import { MyLeaveService } from './my-leave.service';

@Injectable()
class FakeErrorResolverService {
  public createAlert(error: any): void {
  }
}

describe('MyLeaveService', () => {
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
    this.MyLeaveService = this.injector.get(MyLeaveService);
    this.backend = this.injector.get(ConnectionBackend) as MockBackend;
    this.backend.connections.subscribe((connection: any) => this.lastConnection = connection);

    spyOn(console, 'log');
  });

  it('should be created', inject([MyLeaveService], (service: MyLeaveService) => {
    expect(service).toBeTruthy();
  }));

});
