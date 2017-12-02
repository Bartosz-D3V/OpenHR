import { Component, OnInit } from '@angular/core';

import { User } from '../../../shared/domain/user/user';

@Component({
  selector: 'app-core-wrapper',
  templateUrl: './core-wrapper.component.html',
  styleUrls: ['./core-wrapper.component.scss'],
})
export class CoreWrapperComponent implements OnInit {

  public mockUser: User = new User(1299, 'john.test', 'John Test', null);

  constructor() {
  }

  ngOnInit() {
  }

}
