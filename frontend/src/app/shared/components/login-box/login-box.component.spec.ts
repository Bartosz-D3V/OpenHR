import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatButtonModule, MatCardModule, MatFormFieldModule, MatIconModule, MatInputModule } from '@angular/material';

import { LoginBoxComponent } from './login-box.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('LoginBoxComponent', () => {
  let component: LoginBoxComponent;
  let fixture: ComponentFixture<LoginBoxComponent>;

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
      ],
      declarations: [
        LoginBoxComponent,
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginBoxComponent);
    component = fixture.componentInstance;
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

    it('username should mark form as invalid if field is empty', () => {
      component.loginBoxForm.controls['password'].setValue(null);

      expect(component.loginBoxForm.controls['password'].valid).toBeFalsy();
    });

    it('username should mark form as valid if field is not empty', () => {
      component.loginBoxForm.controls['password'].setValue('password123');

      expect(component.loginBoxForm.controls['password'].valid).toBeTruthy();
    });
  });
});
