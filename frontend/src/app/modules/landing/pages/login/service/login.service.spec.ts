import { TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { SystemVariables } from '@config/system-variables';
import { LoginService } from './login.service';
import { Credentials } from '../domain/credentials';

describe('LoginService', () => {
  let service: LoginService;
  let http: HttpTestingController;
  const mockHeaders: HttpHeaders = new HttpHeaders({
    Authorization: 'mock-token',
  });

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LoginService],
      imports: [HttpClientTestingModule],
    });
    service = TestBed.get(LoginService);
    http = TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('API access methods', () => {
    const mockCredentials: Credentials = new Credentials('User', 'password');
    const apiLink: string = SystemVariables.API_URL + '/auth/login';

    it('should query current service URL', fakeAsync(() => {
      service.login(mockCredentials).subscribe();
      http.expectOne(apiLink);
    }));

    describe('login', () => {
      it('should return an Observable of type Response', fakeAsync(() => {
        let result: HttpResponse<null>;
        let error: any;
        service.login(mockCredentials).subscribe((res: HttpResponse<null>) => (result = res), (err: any) => (error = err));
        http
          .expectOne({
            url: apiLink,
            method: 'POST',
          })
          .flush({
            headers: mockHeaders,
          });
        tick();

        expect(error).toBeUndefined();
        expect(result).toBeDefined();
        expect(result.hasOwnProperty('headers')).toBeTruthy();
      }));
    });
  });
});
