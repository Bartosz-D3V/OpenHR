import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ISubscription } from 'rxjs/Subscription';

import { RegularExpressions } from '@shared/constants/regexps/regular-expressions';
import { AccountService } from '@modules/settings/pages/account/service/account.service';
import { NotificationService } from '@shared/services/notification/notification.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { Password } from '@modules/settings/pages/account/domain/password';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss'],
  providers: [
    AccountService,
    NotificationService,
  ],
})
export class AccountComponent implements OnInit, OnDestroy {
  private $accountService: ISubscription;
  public passwordForm: FormGroup;
  public emailForm: FormGroup;

  constructor(private _accountService: AccountService,
              private _notificationService: NotificationService,
              private _errorResolver: ErrorResolverService,
              private _fb: FormBuilder) {
  }

  public buildForm(): void {
    this.passwordForm = this._fb.group({
      oldPassword: ['', Validators.required],
      newPassword: ['', Validators.required],
      newPasswordRepeat: ['', Validators.required],
    });

    this.emailForm = this._fb.group({
      email: ['',
        Validators.compose(
          Validators.required,
          Validators.email,
          Validators.pattern(RegularExpressions.EMAIL)),
      ],
    });
  }

  ngOnInit(): void {
    this.buildForm();
  }

  ngOnDestroy(): void {
    if (this.$accountService !== undefined) {
      this.$accountService.unsubscribe();
    }
  }

  public arePasswordsIdentical(password1: string, password2: string): boolean {
    if (password1 !== password2) {
      this.passwordForm.get('newPasswordRepeat')
        .setErrors({'passwordDoNotMatch': true});
      return false;
    }
    return true;
  }

  public savePassword(): void {
    const password: Password = <Password> this.passwordForm.value;
    this._accountService
      .updatePassword(password)
      .subscribe((() => {
        this._notificationService.openSnackBar('Password updated');
      }), (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }, () => {
        this.passwordForm.reset();
      });
  }
}
