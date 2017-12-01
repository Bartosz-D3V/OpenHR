import { Component, EventEmitter, Input, NgZone, OnInit, Output } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';

import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material';
import { MomentDateAdapter } from '@angular/material-moment-adapter';

import { Moment, MomentInput } from 'moment';
import * as moment from 'moment';

import { NAMED_DATE } from '../../../config/datepicker-format';

@Component({
  selector: 'app-date-range',
  templateUrl: './date-range.component.html',
  styleUrls: ['./date-range.component.scss'],
  providers: [
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: NAMED_DATE},
  ],
})
export class DateRangeComponent implements OnInit {

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
  private mediaMatcher: MediaQueryList = matchMedia(`(max-width: 480px)`);

  public dateRangeGroup: FormGroup = new FormGroup({
    startDate: new FormControl(this.startDate, [
      Validators.required,
    ]),
    endDate: new FormControl(this.endDate, [
      Validators.required,
    ]),
  });

  constructor(private _zone: NgZone) {
    this.mediaMatcher.addListener(mql => _zone.run(() => this.mediaMatcher = mql));
  }

  ngOnInit(): void {
    this.validateStartDateField();
    this.validateEndDateField();
  }

  public validateStartDateField(): void {
    const startDateCtrl: AbstractControl = this.dateRangeGroup.controls['startDate'];
    startDateCtrl.valueChanges.subscribe((value: Moment) => {
      if (moment(value).isAfter(this.endDate)) {
        startDateCtrl.setErrors({'startDateInvalid': true});
      } else {
        this.recalculateNumOfDays(this.startDate, this.endDate);
      }
    });
  }

  public validateEndDateField(): void {
    const endDateCtrl: AbstractControl = this.dateRangeGroup.controls['endDate'];
    endDateCtrl.valueChanges.subscribe((value: MomentInput) => {
      if (this.startDate && moment(value).isBefore(this.startDate)) {
        endDateCtrl.setErrors({'endDateInvalid': true});
      } else {
        this.recalculateNumOfDays(this.startDate, this.endDate);
      }
    });
  }

  public recalculateNumOfDays(startDate: MomentInput, endDate: MomentInput, excludeEndDate?: boolean): void {
    const numberOfDays: number = (moment(endDate).diff(startDate, 'days')) + (excludeEndDate ? 0 : +1);
    this.numberOfDays = numberOfDays;
    this.updateNumberOfDays(numberOfDays);
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

  public isMobile(): boolean {
    return this.mediaMatcher.matches;
  }

}
