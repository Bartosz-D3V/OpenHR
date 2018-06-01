import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ISubscription } from 'rxjs/Subscription';

import { RegularExpressions } from '@shared/constants/regexps/regular-expressions';
import { AccountService } from '@modules/settings/pages/account/service/account.service';
import { NotificationService } from '@shared/services/notification/notification.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { Password } from '@modules/settings/pages/account/domain/password';
import { Email } from '@modules/settings/pages/account/domain/email';
import { CustomAsyncValidatorsService } from '@shared/util/async-validators/custom-async-validators.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss'],
  providers: [AccountService, NotificationService],
})
export class AccountComponent implements OnInit, OnDestroy {
  private $accountService: ISubscription;
  public hidePassword = true;
  public passwordForm: FormGroup;
  public emailForm: FormGroup;

  constructor(
    private _accountService: AccountService,
    private _notificationService: NotificationService,
    private _errorResolver: ErrorResolverService,
    private _asyncValidator: CustomAsyncValidatorsService,
    private _fb: FormBuilder
  ) {}

  public buildForm(): void {
    this.passwordForm = this._fb.group({
      oldPassword: ['', Validators.required],
      newPassword: ['', Validators.required],
      newPasswordRepeat: ['', Validators.required],
    });

    this.emailForm = this._fb.group({
      email: [
        '',
        Validators.compose([Validators.required, Validators.email, Validators.pattern(RegularExpressions.EMAIL)]),
        this._asyncValidator.validateEmailIsFree(),
      ],
    });
  }

  public ngOnInit(): void {
    this.buildForm();
  }

  public ngOnDestroy(): void {
    if (this.$accountService) {
      this.$accountService.unsubscribe();
    }
  }

  public arePasswordsIdentical(password1: string, password2: string): boolean {
    if (password1 !== password2) {
      this.passwordForm.get('newPasswordRepeat').setErrors({ passwordDoNotMatch: true });
      return false;
    }
    return true;
  }

  public savePassword(): void {
    const password: Password = <Password>this.passwordForm.value;
    this._accountService.updatePassword(password).subscribe(
      () => {
        this._notificationService.openSnackBar('Password updated', 'OK');
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      },
      () => {
        this.passwordForm.reset();
      }
    );
  }

  public saveEmail(): void {
    const email: Email = <Email>this.emailForm.value;
    this._accountService.updateEmail(email).subscribe(
      () => {
        this._notificationService.openSnackBar('Email updated', 'OK');
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      },
      () => {
        this.passwordForm.reset();
      }
    );
  }
}
