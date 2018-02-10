import { Component, OnChanges, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { MatRadioChange } from '@angular/material';

import { ISubscription } from 'rxjs/Subscription';

import { MomentInput } from 'moment';

import { ResponsiveHelperService } from '../../../../shared/services/responsive-helper/responsive-helper.service';
import { LeaveApplicationService } from './service/leave-application.service';
import { DateSelectorType } from './domain/date-selector-type.enum';
import { LeaveApplication } from './domain/leave-application';
import { LeaveType } from './domain/leave-type';

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

  private dateRangePickerIsValid: boolean;
  private $leaveTypes: ISubscription;
  public leaveTypes: Array<LeaveType> = [];
  public leaveApplication: LeaveApplication = new LeaveApplication();
  public selectorType: DateSelectorType = DateSelectorType.RANGE;

  public leaveApplicationFormGroup: FormGroup = new FormGroup({
    leaveTypeSelectorController: new FormControl('', [
      Validators.required,
    ]),
    messageController: new FormControl('', [
      Validators.maxLength(500),
    ]),
    dateRangeController: new FormControl('', []),
    singleDateController: new FormControl('', []),
  });

  constructor(private _leaveApplicationService: LeaveApplicationService,
              private _responsiveHelper: ResponsiveHelperService) {
  }

  private setConditionalValidators(): void {
    if (this.selectorType === DateSelectorType.SINGLE) {
      this.leaveApplicationFormGroup.controls['singleDateController']
        .setValidators(Validators.required);
    }
  }

  ngOnInit() {
    this.getLeaveTypes();
  }

  ngOnDestroy() {
    this.$leaveTypes.unsubscribe();
  }

  public getLeaveTypes(): void {
    this.$leaveTypes = this._leaveApplicationService.getLeaveTypes()
      .subscribe((response: Array<LeaveType>) => {
        this.leaveTypes = response;
      });
  }

  public setStartDate(startDate: MomentInput): void {
    this.leaveApplication.startDate = startDate;
  }

  public setEndDate(endDate: MomentInput): void {
    this.leaveApplication.endDate = endDate;
  }

  public setSelector(selector: MatRadioChange): void {
    this.selectorType = selector.value === 'Range' ?
      DateSelectorType.RANGE :
      DateSelectorType.SINGLE;
    this.setConditionalValidators();
  }

  public setLeaveType(leaveType: LeaveType): void {
    this.leaveApplication.leaveType = leaveType;
  }

  public isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }

  public isValid(): boolean {
    switch (this.selectorType) {
      case DateSelectorType.SINGLE:
        return this.leaveApplicationFormGroup.valid;
      case DateSelectorType.RANGE:
      default:
        return this.leaveApplicationFormGroup.valid && this.dateRangePickerIsValid;
    }
  }

  public submitForm(): void {
  }

  public isDateRangePickerValid(isValid: boolean): void {
    this.dateRangePickerIsValid = isValid;
  }
}
