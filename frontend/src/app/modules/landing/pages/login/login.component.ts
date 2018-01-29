import { Component } from '@angular/core';
import { LoginService } from '../../../../shared/components/login-box/service/login.service';
import { JwtHelperService } from '../../../../shared/services/jwt/jwt-helper.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [
    LoginService,
    JwtHelperService,
  ],
})
export class LoginComponent {

  constructor() {
  }

}
