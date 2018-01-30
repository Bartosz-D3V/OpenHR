import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { JwtHelperService } from '../../services/jwt/jwt-helper.service';

@Injectable()
export class MainGuard implements CanActivate {

  constructor(private _router: Router,
              private _jwtHelper: JwtHelperService) {
  }

  canActivate(): boolean {
    if (!this._jwtHelper.isTokenExpired() ||
      this._jwtHelper.hasRole('ROLE_MEMBER')) {
      return true;
    }
    this._router.navigate(['/login']);
    return false;
  }
}
