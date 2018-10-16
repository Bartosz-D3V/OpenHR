import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule, ReactiveFormsModule, ValidationErrors } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import { Observable } from 'rxjs/Observable';
import {
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatSnackBarModule,
  MatTabsModule,
  MatToolbarModule,
} from '@angular/material';

import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { NotificationService } from '@shared/services/notification/notification.service';
import { AccountService } from '@modules/settings/pages/account/service/account.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { Password } from '@modules/settings/pages/account/domain/password';
import { Email } from '@modules/settings/pages/account/domain/email';
import { CustomAsyncValidatorsService } from '@shared/util/async-validators/custom-async-validators.service';
import { AccountComponent } from './account.component';

describe('AccountComponent', () => {
  let component: AccountComponent;
  let fixture: ComponentFixture<AccountComponent>;
  const mockError: HttpErrorResponse = new HttpErrorResponse({
    error: 'Unauthorized',
    status: 401,
  });

  @Injectable()
  class FakeCustomAsyncValidatorsService {
    public validateUsernameIsFree(username: string): ValidationErrors {
      return null;
    }
    public validateEmailIsFree(excludeEmail?: string): ValidationErrors {
      return null;
    }
  }

  @Injectable()
  class FakeAccountService {
    public updatePassword(password: Password): Observable<any> {
      return Observable.of(null);
    }
    public updateEmail(email: Email): Observable<any> {
      return Observable.of(null);
    }
  }

  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {}
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AccountComponent, PageHeaderComponent, CapitalizePipe],
      imports: [
        HttpClientTestingModule,
        NoopAnimationsModule,
        FlexLayoutModule,
        FormsModule,
        ReactiveFormsModule,
        MatToolbarModule,
        MatFormFieldModule,
        MatCardModule,
        MatInputModule,
        MatTabsModule,
        MatIconModule,
        MatSnackBarModule,
      ],
      providers: [
        JwtHelperService,
        NotificationService,
        CustomAsyncValidatorsService,
        {
          provide: CustomAsyncValidatorsService,
          useClass: FakeCustomAsyncValidatorsService,
        },
        {
          provide: AccountService,
          useClass: FakeAccountService,
        },
        {
          provide: ErrorResolverService,
          useClass: FakeErrorResolverService,
        },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('passwordForm', () => {
    describe('oldPassword', () => {
      it('should be valid if the field is not empty', () => {
        component.passwordForm.get('oldPassword').setValue('j.j@mail.com');

        expect(component.passwordForm.get('oldPassword').valid).toBeTruthy();
      });

      it('should be invalid if the field is empty', () => {
        component.passwordForm.get('oldPassword').setValue(null);

        expect(component.passwordForm.get('oldPassword').valid).toBeFalsy();
      });
    });

    describe('newPassword', () => {
      it('should be valid if the field is not empty', () => {
        component.passwordForm.get('newPassword').setValue('j.j@mail.com');

        expect(component.passwordForm.get('newPassword').valid).toBeTruthy();
      });

      it('should be invalid if the field is empty', () => {
        component.passwordForm.get('newPassword').setValue(null);

        expect(component.passwordForm.get('newPassword').valid).toBeFalsy();
      });
    });

    describe('newPasswordRepeat', () => {
      it('should be valid if the field is not empty', () => {
        component.passwordForm.get('newPasswordRepeat').setValue('j.j@mail.com');

        expect(component.passwordForm.get('newPasswordRepeat').valid).toBeTruthy();
      });

      it('should be invalid if the field is empty', () => {
        component.passwordForm.get('newPasswordRepeat').setValue(null);

        expect(component.passwordForm.get('newPasswordRepeat').valid).toBeFalsy();
      });
    });
  });

  describe('emailForm', () => {
    describe('email', () => {
      it('should be valid if the field is not empty', () => {
        spyOn(component['_asyncValidator'], 'validateEmailIsFree').and.returnValue(null);

        component.emailForm.get('email').setValue('j.j@mail.com');

        expect(component.emailForm.get('email').valid).toBeTruthy();
      });

      it('should be invalid if the field is empty', () => {
        spyOn(component['_asyncValidator'], 'validateEmailIsFree').and.returnValue(null);

        component.emailForm.get('email').setValue(null);

        expect(component.emailForm.get('email').valid).toBeFalsy();
      });

      it('should be valid if the value matches the email pattern', () => {
        spyOn(component['_asyncValidator'], 'validateEmailIsFree').and.returnValue(null);

        component.emailForm.get('email').setValue('j.j@mail.com');

        expect(component.emailForm.get('email').valid).toBeTruthy();
      });

      it('should be invalid if the value does not match the email pattern', () => {
        component.emailForm.get('email').setValue('j.jmail.com');

        expect(component.emailForm.get('email').valid).toBeFalsy();
      });
    });
  });

  describe('arePasswordsIdentical method', () => {
    it('arePasswordsIdentical should return true if passwords are the same', () => {
      expect(component.arePasswordsIdentical('password1', 'password1')).toBeTruthy();
      expect(component.passwordForm.get('newPasswordRepeat').hasError('passwordDoNotMatch')).toBeFalsy();
    });

    it('arePasswordsIdentical should return false if passwords are not the same and mark the form as dirty', () => {
      expect(component.arePasswordsIdentical('password1', 'password222')).toBeFalsy();
      expect(component.passwordForm.get('newPasswordRepeat').hasError('passwordDoNotMatch')).toBeTruthy();
    });
  });

  describe('savePassword method', () => {
    it('should call service with password object', () => {
      spyOn(component['_notificationService'], 'openSnackBar');
      spyOn(component['_accountService'], 'updatePassword').and.returnValue(Observable.of(null));

      const mockPassword: Password = new Password('oldPassword', 'newPassword', 'newPassword');
      component.passwordForm.patchValue(mockPassword);
      component.savePassword();

      expect(component['_accountService'].updatePassword).toHaveBeenCalled();
      expect(component['_notificationService'].openSnackBar).toHaveBeenCalledWith('Password updated', 'OK');
    });

    it('should call errorResolver if there was an error', () => {
      spyOn(component['_accountService'], 'updatePassword').and.returnValue(Observable.throw(mockError));
      spyOn(component['_errorResolver'], 'handleError');
      component.savePassword();

      expect(component['_errorResolver'].handleError).toHaveBeenCalledWith(mockError.error);
    });
  });

  describe('saveEmail method', () => {
    it('should call service with email object', () => {
      spyOn(component['_notificationService'], 'openSnackBar');
      spyOn(component['_accountService'], 'updateEmail').and.returnValue(Observable.of(null));

      const mockEmail: Email = new Email('test@test.com');
      component.emailForm.patchValue(mockEmail);
      component.saveEmail();

      expect(component['_accountService'].updateEmail).toHaveBeenCalled();
      expect(component['_notificationService'].openSnackBar).toHaveBeenCalledWith('Email updated', 'OK');
    });

    it('should call errorResolver if there was an error', () => {
      spyOn(component['_accountService'], 'updateEmail').and.returnValue(Observable.throw(mockError));
      spyOn(component['_errorResolver'], 'handleError');
      component.saveEmail();

      expect(component['_errorResolver'].handleError).toHaveBeenCalledWith(mockError.error);
    });
  });
});
