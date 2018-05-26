import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { LoginService } from '@modules/landing/pages/login/service/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [LoginService, JwtHelperService],
})
export class LoginComponent {
  constructor(private _router: Router) {}

  public openApp(authenticated: boolean): void {
    if (authenticated) {
      this._router.navigate(['/app']);
    }
  }
}
