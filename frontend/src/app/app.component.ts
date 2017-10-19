import { Component, OnInit } from '@angular/core';

import { Subject } from './shared/domain/subject/subject';
import { Address } from './shared/domain/subject/address';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {

  public mockSubject: Subject = new Subject('John', 'Test', new Date(1, 2, 1950),
    'Mentor', '12345678', 'test@test.com', new Address('', '', '', '', '', ''));

  ngOnInit(): void {
  }
}
