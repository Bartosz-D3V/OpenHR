import { Component, Input, NgZone, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { MatSidenav } from '@angular/material';

import { User } from '../../domain/user/user';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss'],
})
export class SidenavComponent implements OnInit {

  private mediaMatcher: MediaQueryList = matchMedia(`(max-width: 840px)`);

  @Input()
  public user: User;
  public sidenav: MatSidenav;

  constructor(private _router: Router,
              private _zone: NgZone) {
    this.mediaMatcher.addListener(mql => _zone.run(() => this.mediaMatcher = mql));
  }

  ngOnInit() {
    this._router.events.subscribe(() => {
      if (this.isScreenSmall() && this.sidenav) {
        this.sidenav.close();
      }
    });
  }

  public isScreenSmall(): boolean {
    return this.mediaMatcher.matches;
  }

}
