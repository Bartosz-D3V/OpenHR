import { SidenavItem } from '../sidenav-item/sidenav-item';

export class SidenavList {
  private _itemList: Array<SidenavItem> = [
    new SidenavItem('Dashboard', 'dashboard', true),
    new SidenavItem('My leave', 'card_travel', true),
  ];

  get itemList(): Array<SidenavItem> {
    return this._itemList;
  }
}
