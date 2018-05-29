import { Component, OnInit } from '@angular/core';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { Role } from '@shared/domain/subject/role';

@Component({
  selector: 'app-sidenav-item-list',
  templateUrl: './sidenav-item-list.component.html',
  styleUrls: ['./sidenav-item-list.component.scss'],
  providers: [JwtHelperService],
})
export class SidenavItemListComponent implements OnInit {
  public role: Role;

  constructor(private _jwtHelper: JwtHelperService) {}

  public ngOnInit(): void {
    this.role = this._jwtHelper.getUsersRole() ? this._jwtHelper.getUsersRole()[0] : null;
  }
}
