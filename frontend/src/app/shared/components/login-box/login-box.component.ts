import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Credentials } from './domain/credentials';
import { LoginService } from './service/login.service';
import { JwtHelperService } from '../../services/jwt/jwt-helper.service';

@Component({
  selector: 'app-login-box',
  templateUrl: './login-box.component.html',
  styleUrls: ['./login-box.component.scss'],
  providers: [JwtHelperService],
})
export class LoginBoxComponent implements OnInit {
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
      .subscribe((response: Response) => {
        const token: string = response.headers.get('Bearer');
        this._jwtHelper.saveToken(token);
      });
  }

}
