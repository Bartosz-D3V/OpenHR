import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { MomentDateAdapter } from '@angular/material-moment-adapter';
import { ISubscription } from 'rxjs/Subscription';
import { Moment, MomentInput } from 'moment';
import * as moment from 'moment';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material';

import { NAMED_DATE } from '@config/datepicker-format';
import { CustomValidators } from '@shared/util/validators/custom-validators';
import { ResponsiveHelperService } from '../../services/responsive-helper/responsive-helper.service';
import { ErrorResolverService } from '../../services/error-resolver/error-resolver.service';
import { JwtHelperService } from '../../services/jwt/jwt-helper.service';
import { BankHolidayEngland } from './domain/bank-holiday/england/bank-holiday-england';
import { BankHoliday } from './domain/bank-holiday/england/bank-holiday';
import { DateRangeService } from './service/date-range.service';

@Component({
  selector: 'app-date-range',
  templateUrl: './date-range.component.html',
  styleUrls: ['./date-range.component.scss'],
  providers: [
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
    { provide: MAT_DATE_FORMATS, useValue: NAMED_DATE },
    ResponsiveHelperService,
    DateRangeService,
    ErrorResolverService,
    JwtHelperService,
  ],
})
export class DateRangeComponent implements OnInit, OnDestroy {
  private $bankHolidays: ISubscription;
  private $startDateChange: ISubscription;
  private $endDateChange: ISubscription;

  @Input() public mainFlexProperty?: number;

  @Input() public mobileFlexProperty?: number;

  @Input() public showDescription?;

  @Input() public startDate?: MomentInput;

  @Input() public endDate?: MomentInput;

  @Input() public requireStartDate = true;

  @Input() public requireEndDate = true;

  public numberOfDays: number;

  @Output() public startDateChange: EventEmitter<MomentInput> = new EventEmitter<MomentInput>();

  @Output() public endDateChange: EventEmitter<MomentInput> = new EventEmitter<MomentInput>();

  @Output() public numberOfDaysChange: EventEmitter<number> = new EventEmitter<number>();

  @Output() public isValidChange: EventEmitter<boolean> = new EventEmitter<boolean>();

  public dateRange: FormGroup;

  public bankHolidaysEngland: BankHolidayEngland = new BankHolidayEngland('', []);

  constructor(
    private _dateRangeService: DateRangeService,
    private _responsiveHelper: ResponsiveHelperService,
    private _errorResolver: ErrorResolverService,
    private _fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.buildForm();
    this.getBankHolidays();
  }

  ngOnDestroy(): void {
    this.unsubscribeAll();
  }

  private unsubscribeAll(): void {
    if (this.$startDateChange) {
      this.$startDateChange.unsubscribe();
    }
    if (this.$endDateChange) {
      this.$endDateChange.unsubscribe();
    }
    if (this.$bankHolidays) {
      this.$bankHolidays.unsubscribe();
    }
  }

  private setValidators(): void {
    if (this.requireStartDate) {
      this.dateRange.get('startDate').setValidators(Validators.required);
    }
    if (this.requireEndDate) {
      this.dateRange.get('endDate').setValidators(Validators.required);
    }
  }

  public buildForm(): void {
    this.dateRange = this._fb.group(
      {
        startDate: [this.startDate || ''],
        endDate: [this.endDate || ''],
      },
      { validator: CustomValidators.validateDateRange }
    );
    this.setValidators();
  }

  public recalculateNumOfDays(startDate: MomentInput, endDate: MomentInput, excludeEndDate?: boolean): void {
    let diffDays: number = moment(endDate).diff(startDate, 'days') + (excludeEndDate ? -1 : 0) + 1;
    let diffDaysCounter: number = diffDays;
    while (diffDaysCounter > 0) {
      diffDaysCounter--;
      const date: Moment = moment(endDate).subtract(diffDaysCounter, 'days');
      if (date.isoWeekday() === 6 || date.isoWeekday() === 7 || this.isBankHoliday(date)) {
        diffDays--;
      }
    }

    this.numberOfDays = diffDays;
    this.updateNumberOfDays(diffDays);
  }

  public updateStartDate(startDate: MomentInput): void {
    this.startDate = startDate;
    this.startDateChange.emit(startDate);
    this.isValidChange.emit(this.dateRange.valid);
  }

  public updateEndDate(endDate: MomentInput): void {
    this.endDate = endDate;
    this.endDateChange.emit(endDate);
    this.isValidChange.emit(this.dateRange.valid);
  }

  public updateNumberOfDays(numberOfDays: number): void {
    this.numberOfDays = numberOfDays;
    this.numberOfDaysChange.emit(numberOfDays);
  }

  public isBankHoliday(date: Moment): boolean {
    const bankHolidays: Array<BankHoliday> = this.bankHolidaysEngland.events;
    const foundEvent: BankHoliday = bankHolidays.find((event: BankHoliday) => moment(event.date).isSame(date));
    return !(foundEvent === undefined);
  }

  public getBankHolidays(): void {
    this.$bankHolidays = this._dateRangeService.getBankHolidaysInEnglandAndWales().subscribe(
      (data: BankHolidayEngland) => {
        this.bankHolidaysEngland = data;
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public reset(): void {
    this.dateRange.reset();
  }

  public isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }
}
