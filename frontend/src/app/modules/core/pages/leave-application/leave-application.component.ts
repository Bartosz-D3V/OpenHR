import { Component, OnDestroy, OnInit } from '@angular/core';

import { ISubscription } from 'rxjs/Subscription';

import { MomentInput } from 'moment';

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
  public leaveTypes: Array<string>;
  public leaveApplication: LeaveApplication;

  constructor(private _leaveApplicationService: LeaveApplicationService) {
  }

  ngOnInit() {
    this.getLeaveTypes();
    this.leaveApplication = new LeaveApplication();
  }

  ngOnDestroy() {
    this.$leaveTypes.unsubscribe();
  }

  public getLeaveTypes(): void {
    this.$leaveTypes = this._leaveApplicationService.getLeaveTypes()
      .subscribe((response: Array<string>) => {
        this.leaveTypes = response;
      });
  }

  public setStartDate(startDate: MomentInput): void {
    this.leaveApplication.startDate = startDate;
  }

  public setEndDate(endDate: MomentInput): void {
    this.leaveApplication.endDate = endDate;
  }

}
