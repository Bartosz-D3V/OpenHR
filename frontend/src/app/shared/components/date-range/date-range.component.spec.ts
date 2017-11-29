import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { AbstractControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { MatDatepickerModule, MatInputModule } from '@angular/material';
import { MomentDateModule } from '@angular/material-moment-adapter';
import { FlexLayoutModule } from '@angular/flex-layout';

import { Moment, MomentInput } from 'moment';
import * as moment from 'moment';

import { DateRangeComponent } from './date-range.component';

describe('DateRangeComponent', () => {
  let component: DateRangeComponent;
  let fixture: ComponentFixture<DateRangeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        DateRangeComponent,
      ],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        MomentDateModule,
        FlexLayoutModule,
        MatDatepickerModule,
        MatInputModule,
        NoopAnimationsModule,
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DateRangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('validateStartDateField', () => {
    let startDateCtrl: AbstractControl;

    beforeEach(() => {
      startDateCtrl = component.dateRangeGroup.controls['startDate'];
    });

    it('should set control as invalid if passed date is later than date in passed controller', () => {
      const endDate: MomentInput = '2020-02-08';
      const startDate: MomentInput = '2040-02-12';
      component.endDate = endDate;
      startDateCtrl.setValue(startDate);
      component.validateStartDateField();

      expect(startDateCtrl.valid).toBeFalsy();
    });

    it('should set control as valid if passed date is before than date in passed controller', () => {
      const startDate: MomentInput = '2020-02-08';
      const endDate: MomentInput = '2040-02-12';
      component.endDate = endDate;
      startDateCtrl.setValue(startDate);
      component.validateStartDateField();

      expect(startDateCtrl.valid).toBeTruthy();
    });
  });
});
