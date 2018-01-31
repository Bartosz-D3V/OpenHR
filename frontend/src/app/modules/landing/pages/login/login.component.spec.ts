import { Injectable } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { MatButtonModule, MatCardModule, MatFormFieldModule, MatIconModule, MatInputModule } from '@angular/material';

import { LoginBoxComponent } from '../../../../shared/components/login-box/login-box.component';
import { LoginService } from '../../../../shared/components/login-box/service/login.service';
import { JwtHelperService } from '../../../../shared/services/jwt/jwt-helper.service';
import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  @Injectable()
  class MockLoginService {
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        LoginComponent,
        LoginBoxComponent,
      ],
      imports: [
        ReactiveFormsModule,
        NoopAnimationsModule,
        FormsModule,
        RouterTestingModule,
        MatCardModule,
        MatButtonModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        HttpClientTestingModule,
      ],
      providers: [
        JwtHelperService,
        {provide: LoginService, useClass: MockLoginService},
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('openApp method', () => {
    it('should navigate to /app when user is authenticated', () => {
      spyOn(component['_router'], 'navigate');
      component.openApp(true);

      expect(component['_router'].navigate).toHaveBeenCalled();
      expect(component['_router'].navigate).toHaveBeenCalledWith(['/app']);
    });
  });
});
