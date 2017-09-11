import { Component, Input, OnInit } from '@angular/core';
import { Item } from './sidenav-item/item';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent implements OnInit {

  private itemList: Array<Item>;

  constructor() {
  }

  ngOnInit() {
  }

}
