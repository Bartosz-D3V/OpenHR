import { Component, Input, OnInit } from '@angular/core';

import { User } from '../../domain/user/user';

@Component({
  selector: 'app-avatar',
  templateUrl: './avatar.component.html',
  styleUrls: ['./avatar.component.scss'],
})
export class AvatarComponent implements OnInit {

  @Input()
  public user: User;

  constructor() {
  }

  ngOnInit() {
  }

  public logout(): void {
    window.localStorage.removeItem('openHRAuth');
  }

}
