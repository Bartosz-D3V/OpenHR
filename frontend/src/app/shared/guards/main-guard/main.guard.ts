import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

import { JwtHelperService } from '../../services/jwt/jwt-helper.service';
import { Role } from '../../domain/subject/role';

@Injectable()
export class MainGuard implements CanActivate {
  constructor(private _router: Router, private _jwtHelper: JwtHelperService) {}

  public canActivate(): boolean {
    if (
      !this._jwtHelper.isTokenExpired() &&
      (this._jwtHelper.hasRole(Role.EMPLOYEE) || this._jwtHelper.hasRole(Role.MANAGER) || this._jwtHelper.hasRole(Role.HRTEAMMEMBER))
    ) {
      return true;
    }
    this._router.navigate(['/login']);
    return false;
  }
}
