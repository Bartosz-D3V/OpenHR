import { Component, OnInit } from '@angular/core';

import { SingleChartData } from './domain/single-chart-data';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  public allowanceData: Array<SingleChartData>;

  constructor() {
  }

  ngOnInit() {
  }

}
