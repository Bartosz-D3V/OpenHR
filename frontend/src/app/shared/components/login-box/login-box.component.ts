import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';

import { JwtHelperService } from '../../services/jwt/jwt-helper.service';
import { Credentials } from './domain/credentials';
import { LoginService } from './service/login.service';

@Component({
  selector: 'app-login-box',
  templateUrl: './login-box.component.html',
  styleUrls: ['./login-box.component.scss'],
  providers: [JwtHelperService],
})
export class LoginBoxComponent implements OnInit {
  @Output()
  public onAuthenticated: EventEmitter<boolean> = new EventEmitter<boolean>();

  loginBoxForm: FormGroup;

  constructor(private _fb: FormBuilder,
              private _loginService: LoginService,
              private _jwtHelper: JwtHelperService) {
  }

  ngOnInit() {
    this.createForm();
  }

  public createForm(): void {
    this.loginBoxForm = this._fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  public login(): void {
    const credentials: Credentials = new Credentials(
      this.loginBoxForm.controls['username'].value,
      this.loginBoxForm.controls['password'].value
    );
    this._loginService
      .login(credentials)
      .subscribe((response: HttpResponse<null>) => {
        const token: string = response.headers.get('Authorization');
        this._jwtHelper.saveToken(token);
        this.onAuthenticated.emit(true);
      }, (err: HttpResponse<null>) => {
        this.handleErrorResponse(err);
      });
  }

  handleErrorResponse(err: HttpResponse<null>): void {
    switch (err.status) {
      case 401:
        this.loginBoxForm.controls['password'].setErrors({unauthorized: true});
    }
  }

}
