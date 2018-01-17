import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';

import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material';
import { MomentDateAdapter } from '@angular/material-moment-adapter';

import { ISubscription } from 'rxjs/Subscription';

import { Moment, MomentInput } from 'moment';
import * as moment from 'moment';

import { NAMED_DATE } from '../../../config/datepicker-format';
import { ResponsiveHelperService } from '../../services/responsive-helper/responsive-helper.service';
import { ErrorResolverService } from '../../services/error-resolver/error-resolver.service';
import { BankHolidayEngland } from './domain/bank-holiday/england/bank-holiday-england';
import { BankHoliday } from './domain/bank-holiday/england/bank-holiday';
import { DateRangeService } from './service/date-range.service';

@Component({
  selector: 'app-date-range',
  templateUrl: './date-range.component.html',
  styleUrls: ['./date-range.component.scss'],
  providers: [
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: NAMED_DATE},
    ResponsiveHelperService,
    DateRangeService,
    ErrorResolverService,
  ],
})
export class DateRangeComponent implements OnInit, OnDestroy {

  private $startDateChange: ISubscription;
  private $endDateChange: ISubscription;

  @Input()
  public mainFlexProperty?: number;

  @Input()
  public mobileFlexProperty?: number;

  @Input()
  public startDate?: MomentInput;

  @Input()
  public endDate?: MomentInput;
  public numberOfDays: number;

  @Output()
  public startDateChange: EventEmitter<MomentInput> = new EventEmitter<MomentInput>();

  @Output()
  public endDateChange: EventEmitter<MomentInput> = new EventEmitter<MomentInput>();

  @Output()
  public numberOfDaysChange: EventEmitter<number> = new EventEmitter<number>();

  public bankHolidaysEngland: BankHolidayEngland = new BankHolidayEngland('', []);
  private $bankHolidays: ISubscription;

  public dateRangeGroup: FormGroup = new FormGroup({
    startDate: new FormControl(this.startDate, [
      Validators.required,
    ]),
    endDate: new FormControl(this.endDate, [
      Validators.required,
    ]),
  });

  constructor(private _dateRangeService: DateRangeService,
              private _responsiveHelper: ResponsiveHelperService) {
  }

  ngOnInit(): void {
    this.validateStartDateField();
    this.validateEndDateField();
    this.getBankHolidays();
  }

  ngOnDestroy(): void {
    this.$startDateChange.unsubscribe();
    this.$endDateChange.unsubscribe();
    this.$bankHolidays.unsubscribe();
  }

  public validateStartDateField(): void {
    const startDateCtrl: AbstractControl = this.dateRangeGroup.controls['startDate'];
    this.$startDateChange = startDateCtrl.valueChanges.subscribe((value: Moment) => {
      if (moment(value).isAfter(this.endDate)) {
        startDateCtrl.setErrors({'startDateInvalid': true});
      } else {
        this.recalculateNumOfDays(this.startDate, this.endDate);
      }
    });
  }

  public validateEndDateField(): void {
    const endDateCtrl: AbstractControl = this.dateRangeGroup.controls['endDate'];
    this.$endDateChange = endDateCtrl.valueChanges.subscribe((value: MomentInput) => {
      if (this.startDate && moment(value).isBefore(this.startDate)) {
        endDateCtrl.setErrors({'endDateInvalid': true});
      } else {
        this.recalculateNumOfDays(this.startDate, this.endDate);
      }
    });
  }

  public recalculateNumOfDays(startDate: MomentInput, endDate: MomentInput, excludeEndDate?: boolean): void {
    let diffDays: number = (moment(endDate).diff(startDate, 'days')) + (excludeEndDate ? 0 : +1);
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
  }

  public updateEndDate(endDate: MomentInput): void {
    this.endDate = endDate;
    this.endDateChange.emit(endDate);
  }

  public updateNumberOfDays(numberOfDays: number): void {
    this.numberOfDays = numberOfDays;
    this.numberOfDaysChange.emit(numberOfDays);
  }

  public isBankHoliday(date: Moment): boolean {
    const bankHolidays: Array<BankHoliday> = this.bankHolidaysEngland.events;
    const foundEvent: BankHoliday = bankHolidays.find((event: BankHoliday) => {
      return moment(event.date).isSame(date);
    });
    return !(foundEvent === undefined);
  }

  public isMobile(): boolean {
    return this._responsiveHelper.isMobile();
  }

  public getBankHolidays(): void {
    this.$bankHolidays = this._dateRangeService
      .getBankHolidaysInEnglandAndWales()
      .subscribe((data: BankHolidayEngland) => {
        this.bankHolidaysEngland = data;
      });
  }
}
