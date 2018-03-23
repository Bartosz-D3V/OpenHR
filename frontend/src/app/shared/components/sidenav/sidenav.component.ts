import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {MatSidenav} from '@angular/material';

import {User} from '../../domain/user/user';
import {ResponsiveHelperService} from '../../services/responsive-helper/responsive-helper.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss'],
  providers: [ResponsiveHelperService],
})
export class SidenavComponent implements OnInit {
  @Input() public user: User;
  public sidenav: MatSidenav;

  constructor(private _router: Router, private _responsiveHelper: ResponsiveHelperService) {}

  ngOnInit() {
    this._router.events.subscribe(() => {
      if (this.isScreenSmall() && this.sidenav) {
        this.sidenav.close();
      }
    });
  }

  public isScreenSmall(): boolean {
    return this._responsiveHelper.isSmallTablet();
  }
}
