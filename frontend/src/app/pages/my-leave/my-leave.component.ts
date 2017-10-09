import { Component, OnDestroy, OnInit } from '@angular/core';

import { ISubscription } from 'rxjs/Subscription';

import { MyLeaveService } from './service/my-leave.service';
import { LeaveApplication } from './domain/leave-application';

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
  public leaveApplication: LeaveApplication = new LeaveApplication();

  constructor(private _myLeaveService: MyLeaveService) {
  }

  ngOnInit() {
    this.getLeaveTypes();
  }

  ngOnDestroy() {
    this.$leaveTypes.unsubscribe();
  }

  setEntryDates(): void {
    this.startDate = this.dateRange[0];
    if (this.dateRange[1]) {
      this.endDate = this.dateRange[1];
    }
  }

  clearEntryDates(): void {
    this.startDate = null;
    this.endDate = null;
  }

  clearEndDate(): void {
    this.endDate = null;
  }

  setLeaveDates(): void {
    this.leaveApplication.selectedDays = this.dateRange;
  }

  public getLeaveTypes(): void {
    this.$leaveTypes = this._myLeaveService.getLeaveTypes()
      .subscribe((response: Array<string>) => {
        this.leaveTypes = response;
      });
  }

}
