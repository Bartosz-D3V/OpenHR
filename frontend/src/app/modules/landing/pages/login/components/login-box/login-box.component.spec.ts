import { Injectable } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { AbstractControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatCardModule,
  MatDialogModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatProgressSpinnerModule,
} from '@angular/material';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/throw';

import { LoginBoxComponent } from './login-box.component';
import { LoginService } from '../../service/login.service';
import { Credentials } from '../../domain/credentials';
import { TokenObserverService } from '@shared/services/token-observer/token-observer.service';
import { TokenExpirationModalComponent } from '@shared/components/token-expiration-modal/token-expiration-modal.component';

describe('LoginBoxComponent', () => {
  let component: LoginBoxComponent;
  let fixture: ComponentFixture<LoginBoxComponent>;
  let service: FakeLoginService;

  @Injectable()
  class FakeLoginService {
    public login(credentials: Credentials): Observable<any> {
      return Observable.of(
        new Response(
          {
            refreshToken: 'mock-refresh-token',
          },
          {
            headers: new Headers({
              Authorization: 'mock-token',
            }),
          }
        )
      );
    }

    public unathorizedLogin(credentials: Credentials): Observable<any> {
      return Observable.throw(
        new HttpErrorResponse({
          error: 'Unauthorized',
          status: 401,
        })
      );
    }
  }

  @Injectable()
  class FakeTokenObserverService {
    public observe(): any {}
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        NoopAnimationsModule,
        HttpClientTestingModule,
        FormsModule,
        MatCardModule,
        MatButtonModule,
        MatIconModule,
        MatInputModule,
        MatDialogModule,
        MatFormFieldModule,
        MatProgressSpinnerModule,
      ],
      declarations: [LoginBoxComponent, TokenExpirationModalComponent],
      providers: [
        { provide: LoginService, useClass: FakeLoginService },
        { provide: TokenObserverService, useClass: FakeTokenObserverService },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginBoxComponent);
    component = fixture.componentInstance;
    service = TestBed.get(LoginService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('Login form', () => {
    it('username should mark form as invalid if field is empty', () => {
      component.loginBoxForm.controls['username'].setValue(null);

      expect(component.loginBoxForm.controls['username'].valid).toBeFalsy();
    });

    it('username should mark form as valid if field is not empty', () => {
      component.loginBoxForm.controls['username'].setValue('Jack41');

      expect(component.loginBoxForm.controls['username'].valid).toBeTruthy();
    });

    it('password should mark form as invalid if field is empty', () => {
      component.loginBoxForm.controls['password'].setValue(null);

      expect(component.loginBoxForm.controls['password'].valid).toBeFalsy();
    });

    it('password should mark form as valid if field is not empty', () => {
      component.loginBoxForm.controls['password'].setValue('password123');

      expect(component.loginBoxForm.controls['password'].valid).toBeTruthy();
    });
  });

  describe('login method', () => {
    const mockCredentials: Credentials = new Credentials('User', 'password');

    beforeEach(() => {
      component.loginBoxForm.controls['username'].setValue(mockCredentials.username);
      component.loginBoxForm.controls['password'].setValue(mockCredentials.password);
    });

    it('should retrieve token from header and save it to localStorage', () => {
      spyOn(component['_jwtHelper'], 'saveToken');
      component.login();

      expect(component['_jwtHelper'].saveToken).toHaveBeenCalled();
      expect(component['_jwtHelper'].saveToken).toHaveBeenCalledWith('mock-token');
    });

    it('should emit true once token is saved', () => {
      let authenticated: boolean;
      component.onAuthenticated.subscribe(response => (authenticated = response));
      component.login();

      expect(authenticated).toBeDefined();
      expect(authenticated).toBeTruthy();
    });

    it('should call call error resolver method if failed to authorize', () => {
      spyOn(component, 'handleErrorResponse');
      spyOn(service, 'login').and.callFake(() => {
        return service.unathorizedLogin(new Credentials('', ''));
      });
      component.login();

      expect(component.handleErrorResponse).toHaveBeenCalled();
    });
  });

  describe('handleErrorResponse', () => {
    let passwordCtrl: AbstractControl;

    beforeEach(() => {
      passwordCtrl = component.loginBoxForm.controls['password'];
    });

    it('should set unauthorized error if the returned code is 401', () => {
      const mockResponse: HttpResponse<null> = new HttpResponse({
        status: 401,
      });
      component.handleErrorResponse(mockResponse);

      expect(passwordCtrl.invalid).toBeTruthy();
      expect(passwordCtrl.hasError('unauthorized')).toBeTruthy();
    });
  });
});
