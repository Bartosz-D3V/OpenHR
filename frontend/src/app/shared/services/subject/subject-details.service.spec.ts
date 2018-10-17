import { Injectable } from '@angular/core';
import { TestBed, async, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { SystemVariables } from '@config/system-variables';
import { PersonalDetailsService } from '@modules/core/pages/personal-details/service/personal-details.service';
import { ErrorResolverService } from '../error-resolver/error-resolver.service';
import { Subject } from '../../domain/subject/subject';
import { Address } from '../../domain/subject/address';
import { PersonalInformation } from '../../domain/subject/personal-information';
import { ContactInformation } from '../../domain/subject/contact-information';
import { EmployeeInformation } from '../../domain/subject/employee-information';
import { JwtHelperService } from '../jwt/jwt-helper.service';
import { HrInformation } from '../../domain/subject/hr-information';
import { Employee } from '../../domain/subject/employee';
import { SubjectDetailsService } from './subject-details.service';
import { Role } from '../../domain/subject/role';

describe('PersonalDetailsService', () => {
  const mockPersonalInformation: PersonalInformation = new PersonalInformation('John', 'Xavier', null, new Date());
  const mockAddress: Address = new Address('firstLineAddress', 'secondLineAddress', 'thirdLineAddress', 'postcode', 'city', 'country');
  const mockContactInformation: ContactInformation = new ContactInformation('123456789', 'john.x@company.com', mockAddress);
  const mockEmployeeInformation: EmployeeInformation = new EmployeeInformation(
    'WR 41 45 55 C',
    'Tester',
    'WOR8212',
    '2020-02-08',
    '2020-02-08',
    '123AS'
  );
  const mockHrInformation: HrInformation = new HrInformation(25, 5);
  const mockSubject: Subject = new Employee(
    mockPersonalInformation,
    mockContactInformation,
    mockEmployeeInformation,
    mockHrInformation,
    Role.EMPLOYEE
  );
  let http: HttpTestingController;
  let subjectDetailsService: SubjectDetailsService;
  let errorResolverService: ErrorResolverService;

  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {}
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        JwtHelperService,
        SubjectDetailsService,
        {
          provide: ErrorResolverService,
          useClass: FakeErrorResolverService,
        },
      ],
    });

    http = TestBed.get(HttpTestingController);
    subjectDetailsService = TestBed.get(SubjectDetailsService);
    errorResolverService = TestBed.get(ErrorResolverService);
  }));

  it('should be created', () => {
    expect(subjectDetailsService).toBeTruthy();
  });

  describe('API access method', () => {
    const apiLink: string = SystemVariables.API_URL + '/subjects';

    describe('getSubjectById', () => {
      it('should query current service URL', fakeAsync(() => {
        subjectDetailsService.getSubjectById(223).subscribe();

        http.expectOne(`${apiLink}/223`);
      }));

      it('should return an Observable of type Subject', fakeAsync(() => {
        let result: Object;
        let error: any;
        subjectDetailsService.getSubjectById(223).subscribe((res: Object) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: `${apiLink}/223`,
            method: 'GET',
          })
          .flush(mockSubject);
        tick();

        expect(error).toBeUndefined();
        expect(typeof result).toBe('object');
        expect(JSON.stringify(result)).toEqual(JSON.stringify(mockSubject));
      }));

      it('should resolve error if server is down', fakeAsync(() => {
        let result: Object;
        let error: any;
        subjectDetailsService.getSubjectById(223).subscribe((res: Object) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: `${apiLink}/223`,
            method: 'GET',
          })
          .error(new ErrorEvent('404'));
        tick();
      }));
    });

    describe('getCurrentSubject', () => {
      beforeEach(() => {
        spyOn(subjectDetailsService['_jwtHelper'], 'getSubjectId').and.returnValue(1);
      });

      it('should query current service URL', fakeAsync(() => {
        subjectDetailsService.getCurrentSubject().subscribe();

        http.expectOne(`${apiLink}/1`);
      }));

      it('should return an Observable of type Subject', fakeAsync(() => {
        let result: Object;
        let error: any;
        subjectDetailsService.getCurrentSubject().subscribe((res: Object) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: `${apiLink}/1`,
            method: 'GET',
          })
          .flush(mockSubject);
        tick();

        expect(error).toBeUndefined();
        expect(typeof result).toBe('object');
        expect(JSON.stringify(result)).toEqual(JSON.stringify(mockSubject));
      }));

      it('should resolve error if server is down', fakeAsync(() => {
        let result: Object;
        let error: any;
        subjectDetailsService.getCurrentSubject().subscribe((res: Object) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: `${apiLink}/1`,
            method: 'GET',
          })
          .error(new ErrorEvent('404'));
        tick();
      }));
    });
  });
});
