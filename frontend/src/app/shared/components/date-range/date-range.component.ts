import { Component, Input, OnInit, Output } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';

import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material';
import { MAT_MOMENT_DATE_FORMATS, MomentDateAdapter } from '@angular/material-moment-adapter';

import { Moment, MomentInput } from 'moment';
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
  public startDate?: MomentInput;

  @Output()
  @Input()
  public endDate?: MomentInput;

  @Input()
  public includeEndDate?: boolean;

  @Output()
  public numberOfDays;

  public dateRangeGroup: FormGroup = new FormGroup({
    startDate: new FormControl(this.startDate, [
      Validators.required,
    ]),
    endDate: new FormControl(this.endDate, [
      Validators.required,
    ]),
  });

  ngOnInit(): void {
    this.validateStartDateField();
    this.validateEndDateField();
  }

  public validateStartDateField(): void {
    const startDateCtrl: AbstractControl = this.dateRangeGroup.controls['startDate'];
    startDateCtrl.valueChanges.subscribe((value: Moment) => {
      if (moment(value).isAfter(this.endDate)) {
        startDateCtrl.setErrors({'startDateInvalid': true});
      }
    });
  }

  public validateEndDateField(): void {
    const endDateCtrl: AbstractControl = this.dateRangeGroup.controls['startDate'];
    endDateCtrl.valueChanges.subscribe((value: MomentInput) => {
      if (moment(value).isBefore(this.startDate)) {
        endDateCtrl.setErrors({'endDateInvalid': true});
      }
    });
  }

  public recalculateNumOfDays(startDate: MomentInput, endDate: Moment, includeEndDate: boolean): void {
    this.numberOfDays = moment(startDate).diff(moment(endDate)) + (includeEndDate ? 1 : 0);
  }

}
