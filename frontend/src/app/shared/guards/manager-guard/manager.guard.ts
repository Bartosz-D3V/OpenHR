import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';

import { JwtHelperService } from '../../services/jwt/jwt-helper.service';
import { Role } from '../../domain/subject/role';

@Injectable()
export class ManagerGuard implements CanActivate {
  constructor(private _jwtHelper: JwtHelperService) {}

  public canActivate(): boolean {
    return !this._jwtHelper.isTokenExpired() && (this._jwtHelper.hasRole(Role.MANAGER) || this._jwtHelper.hasRole(Role.HRTEAMMEMBER));
  }
}
