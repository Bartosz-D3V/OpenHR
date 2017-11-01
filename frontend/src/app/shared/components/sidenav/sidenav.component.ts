import { Component, Input, NgZone, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSidenav } from '@angular/material';
import { Subject } from '../../domain/subject/subject';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent implements OnInit {

  @Input()
  public subject: Subject;
  private mediaMatcher: MediaQueryList = matchMedia(`(max-width: 840px)`);
  private sidenav: MatSidenav;

  constructor(private _router: Router,
              private zone: NgZone) {
    this.mediaMatcher.addListener(mql => zone.run(() => this.mediaMatcher = mql));
  }

  ngOnInit() {
    this._router.events.subscribe(() => {
      if (this.isScreenSmall()) {
        this.sidenav.close()
          .catch((error) => console.error(error));
      }
    });
  }

  isScreenSmall(): boolean {
    return this.mediaMatcher.matches;
  }

}
