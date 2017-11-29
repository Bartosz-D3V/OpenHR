import { Component, Input, OnInit, Output } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';

import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material';
import { MAT_MOMENT_DATE_FORMATS, MomentDateAdapter } from '@angular/material-moment-adapter';

import { Moment } from 'moment';
import * as moment from 'moment';

@Component({
  selector: 'app-date-range',
  templateUrl: './date-range.component.html',
  styleUrls: ['./date-range.component.scss'],
  providers: [
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},
  ],
})
export class DateRangeComponent implements OnInit {

  @Input()
  public mainFlexProperty?: number;

  @Input()
  public mobileFlexProperty?: number;

  @Output()
  @Input()
  public startDate?: Moment;

  @Output()
  @Input()
  public endDate?: Moment;

  @Input()
  public includeEndDate?: boolean;

  @Output()
  public numberOfDays;

  public dateRangeGroup: FormGroup = new FormGroup({
    startDate: new FormControl('', [
      Validators.required,
    ]),
    endDate: new FormControl('', [
      Validators.required,
    ]),
  });

  ngOnInit(): void {
    this.validateStartDateField(this.dateRangeGroup.controls['startDate'], this.endDate);
    this.validateEndDateField(this.dateRangeGroup.controls['endDate'], this.startDate);
  }

  public validateStartDateField(startDateCtrl: AbstractControl, endDate: Moment): void {
    startDateCtrl.valueChanges.subscribe((value: Moment) => {
      if (moment(value).isAfter(endDate)) {
        startDateCtrl.setErrors({'startDateInvalid': true});
      } else {
        this.recalculateNumOfDays(moment(value), endDate, this.includeEndDate);
      }
    });
  }

  public validateEndDateField(endDateCtrl: AbstractControl, startDate: Moment): void {
    endDateCtrl.valueChanges.subscribe((value: Moment) => {
      if (moment(value).isBefore(startDate)) {
        endDateCtrl.setErrors({'endDateInvalid': true});
      } else {
        this.recalculateNumOfDays(moment(value), startDate, this.includeEndDate);
      }
    });
  }

  public recalculateNumOfDays(startDate: Moment, endDate: Moment, includeEndDate: boolean): void {
    this.numberOfDays = startDate.diff(endDate) + (includeEndDate ? 1 : 0);
  }

}
