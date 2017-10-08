import { Component, OnDestroy, OnInit } from '@angular/core';

import { ISubscription } from 'rxjs/Subscription';

import { MyLeaveService } from './service/my-leave.service';
import { Leave } from './domain/leave';

@Component({
  selector: 'app-my-leave',
  templateUrl: './my-leave.component.html',
  styleUrls: ['./my-leave.component.scss'],
  providers: [MyLeaveService],
})
export class MyLeaveComponent implements OnInit, OnDestroy {

  public leaveTypes: Array<string>;
  public $leaveTypes: ISubscription;
  public dateRange: Date[];
  public startDate: Date;
  public endDate: Date;
  public selectorType = 'range';
  public leave: Leave = new Leave();

  constructor(private _myLeaveService: MyLeaveService) {
  }

  ngOnInit() {
    this.getLeaveTypes();
  }

  ngOnDestroy() {
    this.$leaveTypes.unsubscribe();
  }

  displayDates(): void {
    this.startDate = this.dateRange[0];
    if (this.dateRange[1]) {
      this.endDate = this.dateRange[1];
    }
  }

  clearDisplayedDates(): void {
    this.startDate = null;
    this.endDate = null;
  }

  clearEndDate(): void {
    this.endDate = null;
  }

  setDates(): void {
    this.leave.selectedDays = this.dateRange;
  }

  public getLeaveTypes(): void {
    this.$leaveTypes = this._myLeaveService.getLeaveTypes()
      .subscribe((response: Array<string>) => {
        this.leaveTypes = response;
      }, (err: any) => {
        this.leaveTypes = [];
      });
  }

}
