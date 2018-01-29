import { Component, Input } from '@angular/core';

import { User } from '../../domain/user/user';
import { JwtHelperService } from '../../services/jwt/jwt-helper.service';

@Component({
  selector: 'app-avatar',
  templateUrl: './avatar.component.html',
  styleUrls: ['./avatar.component.scss'],
  providers: [JwtHelperService],
})
export class AvatarComponent {

  @Input()
  public user: User;

  constructor(private _jwtHelper: JwtHelperService) {
  }

  public logout(): void {
    this._jwtHelper.removeToken();
  }

}
