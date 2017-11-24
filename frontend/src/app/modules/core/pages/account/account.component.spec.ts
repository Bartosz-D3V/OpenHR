import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatToolbarModule } from '@angular/material';

import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { AccountComponent } from './account.component';

describe('AccountComponent', () => {
  let component: AccountComponent;
  let fixture: ComponentFixture<AccountComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AccountComponent,
        PageHeaderComponent,
        CapitalizePipe,
      ],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        MatToolbarModule,
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('currentPasswordController', () => {
    it('should be valid if the field is not empty', () => {
      component.accountFormGroup.controls['currentPasswordController'].setValue('j.j@mail.com');

      expect(component.accountFormGroup.controls['currentPasswordController'].valid).toBeTruthy();
    });

    it('should be invalid if the field is empty', () => {
      component.accountFormGroup.controls['currentPasswordController'].setValue(null);

      expect(component.accountFormGroup.controls['currentPasswordController'].valid).toBeFalsy();
    });
  });

  describe('newPasswordController', () => {
    it('should be valid if the field is not empty', () => {
      component.accountFormGroup.controls['newPasswordController'].setValue('j.j@mail.com');

      expect(component.accountFormGroup.controls['newPasswordController'].valid).toBeTruthy();
    });

    it('should be invalid if the field is empty', () => {
      component.accountFormGroup.controls['newPasswordController'].setValue(null);

      expect(component.accountFormGroup.controls['newPasswordController'].valid).toBeFalsy();
    });
  });

  describe('repeatPasswordController', () => {
    it('should be valid if the field is not empty', () => {
      component.accountFormGroup.controls['repeatPasswordController'].setValue('j.j@mail.com');

      expect(component.accountFormGroup.controls['repeatPasswordController'].valid).toBeTruthy();
    });

    it('should be invalid if the field is empty', () => {
      component.accountFormGroup.controls['repeatPasswordController'].setValue(null);

      expect(component.accountFormGroup.controls['repeatPasswordController'].valid).toBeFalsy();
    });
  });

  describe('emailController', () => {
    it('should be valid if the field is not empty', () => {
      component.accountFormGroup.controls['emailController'].setValue('j.j@mail.com');

      expect(component.accountFormGroup.controls['emailController'].valid).toBeTruthy();
    });

    it('should be invalid if the field is empty', () => {
      component.accountFormGroup.controls['emailController'].setValue(null);

      expect(component.accountFormGroup.controls['emailController'].valid).toBeFalsy();
    });

    it('should be valid if the value matches the email pattern', () => {
      component.accountFormGroup.controls['emailController'].setValue('j.j@mail.com');

      expect(component.accountFormGroup.controls['emailController'].valid).toBeTruthy();
    });

    it('should be invalid if the value does not match the email pattern', () => {
      component.accountFormGroup.controls['emailController'].setValue('j.jmail.com');

      expect(component.accountFormGroup.controls['emailController'].valid).toBeFalsy();
    });
  });


  describe('passwordsAreIdentical', () => {
    it('passwordsAreIdentical should return true if passwords are the same', () => {
      expect(component.passwordsAreIdentical('password1', 'password1')).toBeTruthy();
      expect(component.accountFormGroup.controls['repeatPasswordController']
        .hasError('passwordDoNotMatch')).toBeFalsy();
    });

    it('passwordsAreIdentical should return false if passwords are not the same and mark the form as dirty', () => {
      expect(component.passwordsAreIdentical('password1', 'password222')).toBeFalsy();
      expect(component.accountFormGroup.controls['repeatPasswordController']
        .hasError('passwordDoNotMatch')).toBeTruthy();
    });
  });
});
