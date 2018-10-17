import { TestBed, fakeAsync } from '@angular/core/testing';
import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TokenInterceptorService } from './token-interceptor.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';

describe('TokenInterceptorService', () => {
  let service: TokenInterceptorService;
  let http: HttpClient;
  let httpTest: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        TokenInterceptorService,
        JwtHelperService,
        HttpClient,
        {
          provide: HTTP_INTERCEPTORS,
          useClass: TokenInterceptorService,
          multi: true,
        },
      ],
      imports: [HttpClientTestingModule],
    });
    service = TestBed.get(TokenInterceptorService);
    http = TestBed.get(HttpClient);
    httpTest = TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should add Bearer token to request', fakeAsync(() => {
    let response: any;
    http.get('/api').subscribe((res: any) => {
      response = res;
    });

    httpTest.expectOne(req => req.headers.has('Authorization'));
  }));
});
