import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISubscription } from 'rxjs/Subscription';

import { MomentInput } from 'moment';

import { ResponsiveHelperService } from '../../../../shared/services/responsive-helper/responsive-helper.service';
import { LeaveApplicationService } from './service/leave-application.service';
import { LeaveApplication } from './domain/leave-application';
import { MatRadioChange } from '@angular/material';

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
  public leaveTypes: Array<string> = [];
  public leaveApplication: LeaveApplication;
  public selectorType = 'range';

  public leaveApplicationFormGroup: FormGroup = new FormGroup({
    leaveTypeSelectorController: new FormControl(this.leaveTypes[0], [
      Validators.required,
    ]),
    messageController: new FormControl('', [
      Validators.maxLength(500),
    ]),
    dateRangeController: new FormControl('', []),
    singleDateController: new FormControl('', [
      Validators.required,
    ]),
  });

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
      }, (error: any) => {
        this.leaveTypes = [''];
      });
  }

  public setStartDate(startDate: MomentInput): void {
    this.leaveApplication.startDate = startDate;
  }

  public setEndDate(endDate: MomentInput): void {
    this.leaveApplication.endDate = endDate;
  }

  public setSelector(selector: MatRadioChange): void {
    this.selectorType = selector.value;
  }

  public setLeaveType(leaveType: string): void {
    this.leaveApplication.leaveType = leaveType;
  }

  public isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }

}
