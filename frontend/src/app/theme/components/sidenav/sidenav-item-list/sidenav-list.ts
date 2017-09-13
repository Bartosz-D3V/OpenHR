import { SidenavItem } from '../sidenav-item/sidenav-item';

export class SidenavList {
  private _itemList: Array<SidenavItem> = [
    new SidenavItem('Test', 'star', true)
  ];

  get itemList(): Array<SidenavItem> {
    return this._itemList;
  }
}
