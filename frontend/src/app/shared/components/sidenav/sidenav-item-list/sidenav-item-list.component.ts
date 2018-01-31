import { Component } from '@angular/core';
import { JwtHelperService } from '../../../services/jwt/jwt-helper.service';

@Component({
  selector: 'app-sidenav-item-list',
  templateUrl: './sidenav-item-list.component.html',
  styleUrls: ['./sidenav-item-list.component.scss'],
  providers: [JwtHelperService],
})
export class SidenavItemListComponent {

  constructor(private _jwtHelper: JwtHelperService) {
  }

  hasRole(role: string): boolean {
    return this._jwtHelper.hasRole(role);
  }
}
