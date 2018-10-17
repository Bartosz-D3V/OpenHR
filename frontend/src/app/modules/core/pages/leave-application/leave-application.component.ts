import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { ISubscription } from 'rxjs/Subscription';
import 'rxjs/add/operator/finally';
import 'rxjs/add/operator/retry';
import * as moment from 'moment';

import { NAMED_DATE } from '@config/datepicker-format';
import { SystemVariables } from '@config/system-variables';
import { ResponsiveHelperService } from '@shared/services/responsive-helper/responsive-helper.service';
import { NotificationService } from '@shared/services/notification/notification.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { DateRangeComponent } from '@shared/components/date-range/date-range.component';
import { LeaveType } from '@shared/domain/application/leave-type';
import { LeaveApplication } from '@shared/domain/application/leave-application';
import { BankHoliday } from '@shared/components/date-range/domain/bank-holiday/england/bank-holiday';
import { LeaveApplicationService } from './service/leave-application.service';

@Component({
  selector: 'app-leave-application',
  templateUrl: './leave-application.component.html',
  styleUrls: ['./leave-application.component.scss'],
  providers: [
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
    { provide: MAT_DATE_FORMATS, useValue: NAMED_DATE },
    LeaveApplicationService,
    ResponsiveHelperService,
    NotificationService,
    ErrorResolverService,
  ],
})
export class LeaveApplicationComponent implements OnInit, AfterViewInit, OnDestroy {
  private $leaveTypes: ISubscription;
  private dateRangePickerIsValid: boolean;
  private bankHolidays: Array<BankHoliday>;
  public isLoading: boolean;
  public bankHolidaysDuringLeave: Array<BankHoliday> = [];
  public leaveTypes: Array<LeaveType> = [];
  public leaveApplication: LeaveApplication = new LeaveApplication(null, null, null, null, null, null, null, null);

  @ViewChild('dateRange')
  private dateRangeComponent: DateRangeComponent;

  public leaveApplicationFormGroup: FormGroup = new FormGroup({
    leaveTypeSelectorController: new FormControl('', [Validators.required]),
    messageController: new FormControl('', [Validators.maxLength(500)]),
  });

  public ngAfterViewInit(): void {
    this.dateRangeComponent.bankHolidays.subscribe((data: Array<BankHoliday>) => {
      this.bankHolidays = data;
    });
  }

  constructor(
    private _leaveApplicationService: LeaveApplicationService,
    private _responsiveHelper: ResponsiveHelperService,
    private _notificationService: NotificationService,
    private _errorResolver: ErrorResolverService
  ) {}

  public ngOnInit(): void {
    this.getLeaveTypes();
  }

  public ngOnDestroy(): void {
    this.$leaveTypes.unsubscribe();
  }

  public setStartDate(startDate: moment.MomentInput): void {
    this.leaveApplication.startDate = startDate;
    this.filterComingBankHolidays(this.bankHolidays);
  }

  public setEndDate(endDate: moment.MomentInput): void {
    this.leaveApplication.endDate = endDate;
    this.filterComingBankHolidays(this.bankHolidays);
  }

  public setLeaveType(leaveTypeCategory: string): void {
    this.leaveApplication.leaveType = this.leaveTypes.find((element: LeaveType) => {
      return element.leaveCategory === leaveTypeCategory;
    });
  }

  public getLeaveTypes(): void {
    this.$leaveTypes = this._leaveApplicationService.getLeaveTypes().subscribe(
      (response: Array<LeaveType>) => {
        this.leaveTypes = response;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public isDateRangePickerValid(isValid: boolean): void {
    this.dateRangePickerIsValid = isValid;
  }

  public submitForm(): void {
    this.isLoading = true;
    this._leaveApplicationService
      .submitLeaveApplication(this.leaveApplication)
      .finally(() => (this.isLoading = false))
      .retry(SystemVariables.RETRY_TIMES)
      .subscribe(
        (response: LeaveApplication) => {
          const message = `Application with id ${response.applicationId} has been created`;
          this.leaveApplicationFormGroup.reset();
          this.dateRangeComponent.reset();
          this._notificationService.openSnackBar(message, 'OK');
        },
        (httpErrorResponse: HttpErrorResponse) => {
          this._errorResolver.handleError(httpErrorResponse.error);
        }
      );
  }

  public filterComingBankHolidays(bankHolidays: Array<BankHoliday>): void {
    const startDate: moment.MomentInput = this.leaveApplication.startDate;
    const endDate: moment.MomentInput = this.leaveApplication.endDate;
    this.bankHolidaysDuringLeave = bankHolidays
      .filter((bankHoliday: BankHoliday) => {
        return moment(bankHoliday.date).isBetween(startDate, endDate);
      })
      .sort((bankHoliday1: BankHoliday, bankHoliday2: BankHoliday) => {
        return moment(bankHoliday1.date).diff(bankHoliday2.date, 'days');
      });
  }

  public isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }

  public isValid(): boolean {
    return this.leaveApplicationFormGroup.valid && this.dateRangePickerIsValid;
  }
}
