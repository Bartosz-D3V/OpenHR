import { Injectable } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatButtonModule, MatCardModule, MatFormFieldModule, MatIconModule, MatInputModule } from '@angular/material';

import { Observable } from 'rxjs/Observable';

import { LoginBoxComponent } from './login-box.component';
import { LoginService } from './service/login.service';
import { Credentials } from './domain/credentials';

describe('LoginBoxComponent', () => {
  let component: LoginBoxComponent;
  let fixture: ComponentFixture<LoginBoxComponent>;
  let service: LoginService;

  @Injectable()
  class FakeLoginService {
    login(credentials: Credentials): Observable<any> {
      return Observable.of(new Response(null, {
        headers: new Headers({
          'Authorization': 'mock-token',
        }),
      }));
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        NoopAnimationsModule,
        FormsModule,
        MatCardModule,
        MatButtonModule,
        MatIconModule,
        MatInputModule,
        MatFormFieldModule,
        HttpClientTestingModule,
      ],
      declarations: [
        LoginBoxComponent,
      ],
      providers: [
        {provide: LoginService, useClass: FakeLoginService},
      ],
    })
      .compileComponents();
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
  });
});
