import {Component} from '@angular/core';
import {Router} from '@angular/router';

import {LoginService} from '@shared/components/login-box/service/login.service';
import {JwtHelperService} from '@shared/services/jwt/jwt-helper.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [LoginService, JwtHelperService],
})
export class LoginComponent {
  constructor(private _router: Router) {}

  openApp(authenticated: boolean): void {
    if (authenticated) {
      this._router.navigate(['/app']);
    }
  }
}
