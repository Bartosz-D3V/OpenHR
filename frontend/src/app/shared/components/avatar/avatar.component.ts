import { Component, Input, OnInit } from '@angular/core';
import { Subject } from '../../domain/subject/subject';

@Component({
  selector: 'app-avatar',
  templateUrl: './avatar.component.html',
  styleUrls: ['./avatar.component.scss'],
})
export class AvatarComponent implements OnInit {

  @Input()
  public subject: Subject;

  constructor() {
  }

  ngOnInit() {
  }

  public logout(): void {
    window.localStorage.removeItem('openHRAuth');
  }

}
