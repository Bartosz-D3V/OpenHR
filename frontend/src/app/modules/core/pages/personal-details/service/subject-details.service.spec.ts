import { Injectable } from '@angular/core';
import { TestBed, async, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ErrorResolverService } from '../../../../../shared/services/error-resolver/error-resolver.service';

import { Subject } from '../domain/subject';
import { Address } from '../domain/address';
import { PersonalInformation } from '../domain/personal-information';
import { ContactInformation } from '../domain/contact-information';
import { EmployeeInformation } from '../domain/employee-information';
import { SubjectDetailsService } from './subject-details.service';
import { SystemVariables } from '../../../../../config/system-variables';

describe('PersonalDetailsService', () => {
  const mockPersonalInformation: PersonalInformation = new PersonalInformation(null, new Date());
  const mockAddress: Address = new Address('firstLineAddress', 'secondLineAddress', 'thirdLineAddress', 'postcode', 'city', 'country');
  const mockContactInformation: ContactInformation = new ContactInformation('123456789', 'john.x@company.com', mockAddress);
  const mockEmployeeInformation: EmployeeInformation = new EmployeeInformation('WR 41 45 55 C', 'Tester', '123AS', new Date(), new Date());
  const mockSubject: Subject = new Subject('John', 'Xavier', mockPersonalInformation, mockContactInformation,
    mockEmployeeInformation);
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
        SubjectDetailsService,
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
    });

    http = TestBed.get(HttpTestingController);
    personalDetailsService = TestBed.get(SubjectDetailsService);
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

    const apiLink: string = SystemVariables.API_URL + 'my-details';

    it('should query current service URL', fakeAsync(() => {
      personalDetailsService.getCurrentSubject().subscribe();

      http.expectOne(apiLink);
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
          url: apiLink,
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
          url: apiLink,
          method: 'GET',
        }).error(new ErrorEvent('404'));
        tick();

        expect(personalDetailsService['handleError']).toHaveBeenCalled();
      }));

    });

  });

});
