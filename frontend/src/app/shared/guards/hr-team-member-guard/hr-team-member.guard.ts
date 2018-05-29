import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';

import { Role } from '../../domain/subject/role';
import { JwtHelperService } from '../../services/jwt/jwt-helper.service';

@Injectable()
export class HrTeamMemberGuard implements CanActivate {
  constructor(private _jwtHelper: JwtHelperService) {}

  public canActivate(): boolean {
    return !this._jwtHelper.isTokenExpired() && this._jwtHelper.hasRole(Role.HRTEAMMEMBER);
  }
}
