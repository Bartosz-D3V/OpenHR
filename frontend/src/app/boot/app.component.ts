import { Component, OnInit } from '@angular/core';

import { User } from '../shared/domain/user/user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {

  public mockUser: User = new User(1299, 'john.test', 'John Test');

  ngOnInit(): void {
  }
}
