import { SidenavItem } from '../sidenav-item/sidenav-item';

export class SidenavList {
  private _itemList: Array<SidenavItem> = [
    new SidenavItem('Dashboard', 'dashboard', '', true),
    new SidenavItem('My details', 'account_circle', '/my-details', true),
    new SidenavItem('My leave', 'card_travel', '/my-leave', true),
  ];

  get itemList(): Array<SidenavItem> {
    return this._itemList;
  }
}
