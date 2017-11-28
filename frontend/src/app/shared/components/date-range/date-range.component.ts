import { Component, Input, OnInit, Output } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-date-range',
  templateUrl: './date-range.component.html',
  styleUrls: ['./date-range.component.scss'],
})
export class DateRangeComponent implements OnInit {

  @Input()
  public mainFlexProperty: number;

  @Input()
  public mobileFlexProperty: number;

  @Output()
  @Input()
  public startDate: Date;

  @Output()
  @Input()
  public endDate: Date;

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

  public validateStartDateField(startDateCtrl: AbstractControl, endDate: Date): void {
    startDateCtrl.valueChanges.subscribe((value: Date) => {
      if (value > endDate) {
        startDateCtrl.setErrors({'startDateInvalid': true});
      }
    });
  }

  public validateEndDateField(endDateCtrl: AbstractControl, startDate: Date): void {
    endDateCtrl.valueChanges.subscribe((value: Date) => {
      if (value < startDate) {
        endDateCtrl.setErrors({'endDateInvalid': true});
      }
    });
  }

}
