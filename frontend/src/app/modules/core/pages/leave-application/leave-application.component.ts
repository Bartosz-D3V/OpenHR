import { Component, OnDestroy, OnInit } from '@angular/core';

import { ISubscription } from 'rxjs/Subscription';

import { LeaveApplicationService } from './service/leave-application.service';
import { LeaveApplication } from './domain/leave-application';

@Component({
  selector: 'app-leave-application',
  templateUrl: './leave-application.component.html',
  styleUrls: ['./leave-application.component.scss'],
  providers: [LeaveApplicationService],
})
export class LeaveApplicationComponent implements OnInit, OnDestroy {

  private $leaveTypes: ISubscription;
  public leaveTypes: Array<String>;
  public dateRange: Date[];
  public startDate: Date;
  public endDate: Date;
  public selectorType = 'range';
  public leaveApplication: LeaveApplication = new LeaveApplication();

  constructor(private leaveApplicationService: LeaveApplicationService) {
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

  getLeaveTypes(): void {
    this.$leaveTypes = this.leaveApplicationService.getLeaveTypes()
      .subscribe((response: Array<string>) => {
        this.leaveTypes = response;
      });
  }

}
