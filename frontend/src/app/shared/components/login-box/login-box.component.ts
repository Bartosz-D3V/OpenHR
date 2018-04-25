import { Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';

import { JwtHelperService } from '../../services/jwt/jwt-helper.service';
import { Credentials } from './domain/credentials';
import { LoginService } from './service/login.service';
import { ISubscription } from 'rxjs/Subscription';

@Component({
  selector: 'app-login-box',
  templateUrl: './login-box.component.html',
  styleUrls: ['./login-box.component.scss'],
  providers: [JwtHelperService],
})
export class LoginBoxComponent implements OnInit, OnDestroy {
  private $loginService: ISubscription;

  @Output() public onAuthenticated: EventEmitter<boolean> = new EventEmitter<boolean>();

  public loginBoxForm: FormGroup;

  constructor(private _fb: FormBuilder, private _loginService: LoginService, private _jwtHelper: JwtHelperService) {}

  ngOnInit() {
    this.createForm();
  }

  ngOnDestroy(): void {
    if (this.$loginService !== undefined) {
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
    const credentials: Credentials = <Credentials>this.loginBoxForm.value;
    this.$loginService = this._loginService.login(credentials).subscribe(
      (response: HttpResponse<null>) => {
        const token: string = response.headers.get('Authorization');
        this._jwtHelper.saveToken(token);
        this._jwtHelper.startIATObserver(token);
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
