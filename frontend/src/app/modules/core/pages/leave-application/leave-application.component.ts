import { Component, OnDestroy, OnInit } from '@angular/core';

import { ISubscription } from 'rxjs/Subscription';

import { MomentInput } from 'moment';

import { ResponsiveHelperService } from '../../../../shared/services/responsive-helper/responsive-helper.service';
import { LeaveApplicationService } from './service/leave-application.service';
import { LeaveApplication } from './domain/leave-application';

@Component({
  selector: 'app-leave-application',
  templateUrl: './leave-application.component.html',
  styleUrls: ['./leave-application.component.scss'],
  providers: [
    LeaveApplicationService,
    ResponsiveHelperService,
  ],
})
export class LeaveApplicationComponent implements OnInit, OnDestroy {

  private $leaveTypes: ISubscription;
  public leaveTypes: Array<string>;
  public leaveApplication: LeaveApplication;
  public selectorType = 'range';

  constructor(private _leaveApplicationService: LeaveApplicationService,
              private _responsiveHelper: ResponsiveHelperService) {
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

  public isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }

}
