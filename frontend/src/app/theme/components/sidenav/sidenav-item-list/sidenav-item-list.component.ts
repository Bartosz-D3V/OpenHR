import { Component } from '@angular/core';
import { Item } from '../sidenav-item/item';

@Component({
  selector: 'app-sidenav-item-list',
  templateUrl: './sidenav-item-list.component.html',
  styleUrls: ['./sidenav-item-list.component.scss']
})
export class SidenavItemListComponent {

  public itemList: Array<Item> = [
    new Item('Test', 'star', true)
  ];

}
