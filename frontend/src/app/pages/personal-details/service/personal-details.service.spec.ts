import { Injectable } from '@angular/core';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TestBed, inject, async, fakeAsync, tick } from '@angular/core/testing';

import { Subject } from '../../../shared/domain/subject/subject';
import { Address } from '../../../shared/domain/subject/address';
import { PersonalDetailsService } from './personal-details.service';
import { ErrorResolverService } from '../../../shared/services/error-resolver/error-resolver.service';
import { LeaveApplicationService } from '../../leave-application/service/leave-application.service';

describe('PersonalDetailsService', () => {
  const mockSubject = new Subject('John', 'Test', new Date(1, 2, 1950),
    'Mentor', '12345678', 'test@test.com', new Address('', '', '', '', '', ''));
  let http: HttpTestingController;
  let personalDetailsService;
  let errorResolverService: ErrorResolverService;

  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
      ],
      providers: [
        PersonalDetailsService,
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
    });

    http = TestBed.get(HttpTestingController);
    personalDetailsService = TestBed.get(PersonalDetailsService);
    errorResolverService = TestBed.get(ErrorResolverService);
    spyOn(console, 'log');
  }));

  it('should be created', () => {
    expect(personalDetailsService).toBeTruthy();
  });

  describe('handleError method', () => {
    const mockError = 'Mock Error';

    it('should log actual error', () => {
      personalDetailsService['handleError'](mockError);

      expect(console.log).toHaveBeenCalledTimes(1);
      expect(console.log).toHaveBeenCalledWith('An error occurred', mockError);
    });

    it('should trigger createAlert method', () => {
      spyOn(errorResolverService, 'createAlert');
      personalDetailsService['handleError'](mockError);

      expect(errorResolverService.createAlert).toHaveBeenCalledTimes(1);
      expect(errorResolverService.createAlert).toHaveBeenCalledWith(mockError);
    });

  });

  describe('API access method', () => {

    it('should query current service URL', fakeAsync(() => {
      personalDetailsService.getCurrentSubject().subscribe();

      http.expectOne('app/my-details');
    }));

    describe('getCurrentSubject', () => {

      it('should return an Observable of type Subject', fakeAsync(() => {
        let result: Object;
        let error: any;
        personalDetailsService.getCurrentSubject()
          .subscribe(
            (res: Object) => result = res,
            (err: any) => error = err);
        http.expectOne({
          url: 'app/my-details',
          method: 'GET',
        }).flush(mockSubject);
        tick();

        expect(error).toBeUndefined();
        expect(typeof result).toBe('object');
        expect(JSON.stringify(result)).toEqual(JSON.stringify(mockSubject));
      }));

      it('should resolve error if server is down', fakeAsync(() => {
        spyOn(personalDetailsService, 'handleError');
        let result: Object;
        let error: any;
        personalDetailsService.getCurrentSubject()
          .subscribe(
            (res: Object) => result = res,
            (err: any) => error = err);
        http.expectOne({
          url: 'app/my-details',
          method: 'GET',
        }).error(new ErrorEvent('404'));
        tick();

        expect(personalDetailsService['handleError']).toHaveBeenCalled();
      }));

    });

  });

});
