import { Component, Input, OnInit } from '@angular/core';

import { User } from '../../domain/user/user';

@Component({
  selector: 'app-avatar',
  templateUrl: './avatar.component.html',
  styleUrls: ['./avatar.component.scss'],
})
export class AvatarComponent {

  @Input()
  public user: User;

  public logout(): void {
    window.localStorage.removeItem('openHRAuth');
  }

}
