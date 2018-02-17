import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE, MatRadioChange } from '@angular/material';
import { MomentDateAdapter } from '@angular/material-moment-adapter';

import { ISubscription } from 'rxjs/Subscription';

import { MomentInput } from 'moment';

import { NAMED_DATE } from '../../../../config/datepicker-format';
import { ResponsiveHelperService } from '../../../../shared/services/responsive-helper/responsive-helper.service';
import { NotificationService } from '../../../../shared/services/notification/notification.service';
import { LeaveApplication } from '../../../../shared/domain/leave-application/leave-application';
import { LeaveType } from '../../../../shared/domain/leave-application/leave-type';
import { DateRangeComponent } from '../../../../shared/components/date-range/date-range.component';
import { DateSelectorType } from './enumeration/date-selector-type.enum';
import { LeaveApplicationService } from './service/leave-application.service';

@Component({
  selector: 'app-leave-application',
  templateUrl: './leave-application.component.html',
  styleUrls: ['./leave-application.component.scss'],
  providers: [
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: NAMED_DATE},
    LeaveApplicationService,
    ResponsiveHelperService,
    NotificationService,
  ],
})
export class LeaveApplicationComponent implements OnInit, OnDestroy {

  @ViewChild('dateRange')
  private dateRangeComponent: DateRangeComponent;

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
              private _responsiveHelper: ResponsiveHelperService,
              private _notificationService: NotificationService) {
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

  setStartDate(startDate: MomentInput): void {
    this.leaveApplication.startDate = startDate;
  }

  setEndDate(endDate: MomentInput): void {
    this.leaveApplication.endDate = endDate;
  }

  setSelector(selector: MatRadioChange): void {
    this.selectorType = selector.value === 'Range' ?
      DateSelectorType.RANGE :
      DateSelectorType.SINGLE;
    this.setConditionalValidators();
  }

  setLeaveType(leaveTypeCategory: string): void {
    this.leaveApplication.leaveType = this.leaveTypes.find((element: LeaveType) => {
      return element.leaveCategory === leaveTypeCategory;
    });
  }

  public getLeaveTypes(): void {
    this.$leaveTypes = this._leaveApplicationService.getLeaveTypes()
      .subscribe((response: Array<LeaveType>) => {
        this.leaveTypes = response;
      });
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

  public isDateRangePickerValid(isValid: boolean): void {
    this.dateRangePickerIsValid = isValid;
  }

  public submitForm(): void {
    this._leaveApplicationService
      .submitLeaveApplication(this.leaveApplication)
      .subscribe((response: LeaveApplication) => {
        const message = `Application with id ${response.applicationId} has been created`;
        this.leaveApplicationFormGroup.reset();
        this.dateRangeComponent.reset();
        this._notificationService.openSnackBar(message, 'OK');
      });
  }
}
