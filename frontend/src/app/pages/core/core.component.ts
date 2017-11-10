import { Component, OnInit } from '@angular/core';

import { User } from '../../shared/domain/user/user';

@Component({
  selector: 'app-core',
  templateUrl: './core.component.html',
  styleUrls: ['./core.component.scss']
})
export class CoreComponent implements OnInit {

  public mockUser: User = new User(1299, 'john.test', 'John Test');

  constructor() {
  }

  ngOnInit() {
  }

}
