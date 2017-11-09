import { SidenavItem } from '../sidenav-item/sidenav-item';

export class SidenavList {
  private _itemList: Array<SidenavItem> = [
    new SidenavItem('Dashboard', 'dashboard', '', true),
    new SidenavItem('Personal details', 'account_circle', '/personal-details', true),
    new SidenavItem('Leave application', 'card_travel', '/leave-application', true),
    new SidenavItem('Delegation', 'airplanemode_active', '/delegation', true),
    new SidenavItem('Report', 'show_chart', '/report', true),
    new SidenavItem('About', 'info_outline', '/about', true),
  ];

  get itemList(): Array<SidenavItem> {
    return this._itemList;
  }
}
