import { Component, NgZone, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { MdSidenav } from '@angular/material';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class SidenavComponent implements OnInit {

  private mediaMatcher: MediaQueryList = matchMedia('max-width: 840px');

  @ViewChild(MdSidenav)
  public sidenav: MdSidenav;

  constructor(private _router: Router, private zone: NgZone) {
    this.mediaMatcher.addListener(mql => zone.run(() => this.mediaMatcher = mql));
  }

  ngOnInit() {
    this._router.events.subscribe(() => {
      if (this.isScreenSmall()) {
        this.sidenav.close();
      }
    });
  }

  isScreenSmall(): boolean {
    return this.mediaMatcher.matches;
  }

}
