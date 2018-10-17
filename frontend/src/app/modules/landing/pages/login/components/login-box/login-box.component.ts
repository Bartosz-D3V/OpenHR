import { Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { ISubscription } from 'rxjs/Subscription';
import 'rxjs/add/operator/finally';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { TokenObserverService } from '@shared/services/token-observer/token-observer.service';
import { RefreshToken } from 'app/modules/landing/pages/login/domain/refresh-token';
import { Credentials } from '../../domain/credentials';
import { LoginService } from '../../service/login.service';

@Component({
  selector: 'app-login-box',
  templateUrl: './login-box.component.html',
  styleUrls: ['./login-box.component.scss'],
  providers: [JwtHelperService, TokenObserverService],
})
export class LoginBoxComponent implements OnInit, OnDestroy {
  private $loginService: ISubscription;
  public isLoading: boolean;

  @Output()
  public onAuthenticated: EventEmitter<boolean> = new EventEmitter<boolean>();

  public loginBoxForm: FormGroup;

  constructor(private _loginService: LoginService, private _jwtHelper: JwtHelperService, private _fb: FormBuilder) {}

  public ngOnInit(): void {
    this.createForm();
  }

  public ngOnDestroy(): void {
    if (this.$loginService) {
      this.$loginService.unsubscribe();
    }
  }

  public createForm(): void {
    this.loginBoxForm = this._fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  public login(): void {
    this.isLoading = true;
    const credentials: Credentials = <Credentials>this.loginBoxForm.value;
    this.$loginService = this._loginService
      .login(credentials)
      .finally(() => (this.isLoading = false))
      .subscribe(
        (response: HttpResponse<RefreshToken>) => {
          const token: string = response.headers.get('Authorization');
          const refreshToken: string = response.body.refreshToken;
          this._jwtHelper.saveToken(token);
          this._jwtHelper.saveRefreshToken(refreshToken);
          this.onAuthenticated.emit(true);
        },
        (err: HttpResponse<null>) => {
          this.handleErrorResponse(err);
        }
      );
  }

  public handleErrorResponse(err: HttpResponse<null>): void {
    switch (err.status) {
      case 401:
        this.loginBoxForm.controls['password'].setErrors({ unauthorized: true });
    }
  }
}
