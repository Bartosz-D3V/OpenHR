import { Component } from '@angular/core';
import { SidenavList } from './sidenav-list';
import { SidenavItem } from '../sidenav-item/sidenav-item';

@Component({
  selector: 'app-sidenav-item-list',
  templateUrl: './sidenav-item-list.component.html',
  styleUrls: ['./sidenav-item-list.component.scss']
})
export class SidenavItemListComponent {

  public itemList: Array<SidenavItem>;

  constructor() {
    this.itemList = new SidenavList().itemList;
  }
}
