import { Injectable } from '@angular/core';
import { TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ConfigService } from './config.service';
import { ErrorResolverService } from '../error-resolver/error-resolver.service';

describe('ConfigService', () => {
  let http: HttpTestingController;
  let configService;
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
        ConfigService,
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
    });
    http = TestBed.get(HttpTestingController);
    configService = TestBed.get(ConfigService);
    errorResolverService = TestBed.get(ErrorResolverService);
    spyOn(console, 'log');
  });

  it('should be created', () => {
    expect(configService).toBeTruthy();
  });

  describe('handleError method', () => {
    const mockError = 'Mock Error';

    it('should log actual error', () => {
      configService['handleError'](mockError);

      expect(console.log).toHaveBeenCalledTimes(1);
      expect(console.log).toHaveBeenCalledWith('An error occurred', mockError);
    });

    it('should trigger createAlert method', () => {
      spyOn(errorResolverService, 'createAlert');
      configService['handleError'](mockError);

      expect(errorResolverService.createAlert).toHaveBeenCalledTimes(1);
      expect(errorResolverService.createAlert).toHaveBeenCalledWith(mockError);
    });
  });

  describe('API access method', () => {

    describe('getContractTypes', () => {
      const apiLink = '/api/config/contractTypes';

      it('should query current service URL', fakeAsync(() => {
        configService.getContractTypes().subscribe();

        http.expectOne('/api/config/contractTypes');
      }));


      it('should return array with strings if response is valid', fakeAsync(() => {
        const mockContractTypes: Array<string> = ['Permanent', 'Temporary'];
        let result: Object;
        let error: any;
        configService.getContractTypes()
          .subscribe(
            (res: Object) => result = res,
            (err: any) => error = err);
        http.expectOne({
          url: apiLink,
          method: 'GET',
        }).flush(mockContractTypes);
        tick();

        expect(error).toBeUndefined();
        expect(typeof result).toBe('object');
        expect(JSON.stringify(result)).toEqual(JSON.stringify(mockContractTypes));
      }));

      it('should resolve error if server is down', fakeAsync(() => {
        spyOn(configService, 'handleError');
        let result: Object;
        let error: any;
        configService.getContractTypes()
          .subscribe(
            (res: Object) => result = res,
            (err: any) => error = err);
        http.expectOne({
          url: apiLink,
          method: 'GET',
        }).error(new ErrorEvent('404'));
        tick();

        expect(configService['handleError']).toHaveBeenCalled();
      }));
    });

    describe('getLeaveTypes', () => {
      const apiLink = '/api/config/leaveTypes';

      it('should query current service URL', fakeAsync(() => {
        configService.getLeaveTypes().subscribe();

        http.expectOne('/api/config/leaveTypes');
      }));


      it('should return array with strings if response is valid', fakeAsync(() => {
        const mockLeaveTypes: Array<string> = ['Holiday', 'Maternity leave'];
        let result: Object;
        let error: any;
        configService.getLeaveTypes()
          .subscribe(
            (res: Object) => result = res,
            (err: any) => error = err);
        http.expectOne({
          url: apiLink,
          method: 'GET',
        }).flush(mockLeaveTypes);
        tick();

        expect(error).toBeUndefined();
        expect(typeof result).toBe('object');
        expect(JSON.stringify(result)).toEqual(JSON.stringify(mockLeaveTypes));
      }));

      it('should resolve error if server is down', fakeAsync(() => {
        spyOn(configService, 'handleError');
        let result: Object;
        let error: any;
        configService.getLeaveTypes()
          .subscribe(
            (res: Object) => result = res,
            (err: any) => error = err);
        http.expectOne({
          url: apiLink,
          method: 'GET',
        }).error(new ErrorEvent('404'));
        tick();

        expect(configService['handleError']).toHaveBeenCalled();
      }));
    });
  });

});
