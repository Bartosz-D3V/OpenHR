import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { MatSidenav } from '@angular/material';

import { ResponsiveHelperService } from '../../services/responsive-helper/responsive-helper.service';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss'],
  providers: [ResponsiveHelperService],
})
export class SidenavComponent implements OnInit {
  @Input()
  public user: LightweightSubject;
  public sidenav: MatSidenav;

  constructor(private _router: Router, private _responsiveHelper: ResponsiveHelperService) {}

  public ngOnInit(): void {
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
