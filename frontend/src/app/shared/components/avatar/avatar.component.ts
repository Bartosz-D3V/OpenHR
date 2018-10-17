import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

import { JwtHelperService } from '../../services/jwt/jwt-helper.service';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';

@Component({
  selector: 'app-avatar',
  templateUrl: './avatar.component.html',
  styleUrls: ['./avatar.component.scss'],
  providers: [JwtHelperService],
})
export class AvatarComponent {
  @Input()
  public user: LightweightSubject;

  constructor(private _jwtHelper: JwtHelperService, private _router: Router) {}

  public logout(): void {
    this._jwtHelper.removeToken();
    this._router.navigate(['login']);
  }
}
