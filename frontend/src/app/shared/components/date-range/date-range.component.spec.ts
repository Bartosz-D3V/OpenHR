import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { AbstractControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { MatDatepickerModule, MatInputModule } from '@angular/material';
import { MomentDateModule } from '@angular/material-moment-adapter';
import { FlexLayoutModule } from '@angular/flex-layout';

import { DateRangeComponent } from './date-range.component';
import moment = require('moment');
import { MomentInput } from 'moment';

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

  it('ngOnInit should trigger validation methods', () => {
    spyOn(component, 'validateStartDateField');
    spyOn(component, 'validateEndDateField');
    component.ngOnInit();

    expect(component.validateStartDateField).toHaveBeenCalled();
    expect(component.validateEndDateField).toHaveBeenCalled();
  });

  describe('date range form group', () => {
    describe('startDate controller', () => {
      let startDateCtrl: AbstractControl;

      beforeEach(() => {
        startDateCtrl = component.dateRangeGroup.controls['startDate'];
      });

      it('should be marked invalid if input is empty', () => {
        startDateCtrl.setValue(null);

        expect(startDateCtrl.valid).toBeFalsy();
      });

      it('should be marked valid if input is not empty', () => {
        startDateCtrl.setValue(moment([2017, 0, 1]));

        expect(startDateCtrl.valid).toBeTruthy();
      });
    });

    describe('endDate controller', () => {
      let endDateCtrl: AbstractControl;

      beforeEach(() => {
        endDateCtrl = component.dateRangeGroup.controls['endDate'];
      });

      it('should be marked invalid if input is empty', () => {
        endDateCtrl.setValue(null);

        expect(endDateCtrl.valid).toBeFalsy();
      });

      it('should be marked valid if input is not empty', () => {
        endDateCtrl.setValue(moment([2017, 5, 1]));

        expect(endDateCtrl.valid).toBeTruthy();
      });
    });
  });

  describe('validateStartDateField', () => {
    let startDateCtrl: AbstractControl;

    beforeEach(() => {
      startDateCtrl = component.dateRangeGroup.controls['startDate'];
    });

    it('should set control as invalid if passed date is later than date in controller', () => {
      component.endDate = '2020-02-08';
      startDateCtrl.setValue('2040-02-12');
      component.validateStartDateField();

      expect(startDateCtrl.valid).toBeFalsy();
    });

    it('should set control as valid if passed date is before than date in controller', () => {
      component.endDate = '2040-02-12';
      startDateCtrl.setValue('2020-02-08');
      component.validateStartDateField();

      expect(startDateCtrl.valid).toBeTruthy();
    });
  });

  describe('validateEndDateField', () => {
    let endDateCtrl: AbstractControl;

    beforeEach(() => {
      endDateCtrl = component.dateRangeGroup.controls['endDate'];
    });

    it('should set control as invalid if passed date is earlier than date in controller', () => {
      component.startDate = '2040-02-12';
      endDateCtrl.setValue('2020-02-08');
      component.validateEndDateField();

      expect(endDateCtrl.valid).toBeFalsy();
    });

    it('should set control as valid if passed date is later than date in controller', () => {
      component.startDate = '2020-02-08';
      endDateCtrl.setValue('2040-02-12');
      component.validateEndDateField();

      expect(endDateCtrl.valid).toBeTruthy();
    });
  });

  describe('recalculateNumOfDays', () => {
    it('should set numberOfDays including the end date', () => {
      component.recalculateNumOfDays('2019-05-10', '2019-05-20', false);

      expect(component.numberOfDays).toBeDefined();
      expect(component.numberOfDays).toEqual(11);
    });

    it('should set numberOfDays including the end date by default', () => {
      component.recalculateNumOfDays('2019-05-10', '2019-05-20');

      expect(component.numberOfDays).toBeDefined();
      expect(component.numberOfDays).toEqual(11);
    });

    it('should set numberOfDays excluding the end date', () => {
      component.recalculateNumOfDays('2019-05-10', '2019-05-20', true);

      expect(component.numberOfDays).toBeDefined();
      expect(component.numberOfDays).toEqual(10);
    });

    it('should emmit new value through EventEmmiter', () => {
      let result: number;
      spyOn(component, 'updateNumberOfDays').and.callThrough();
      component.numberOfDaysChange.subscribe((newNumberOfDays: number) => {
        result = newNumberOfDays;
      });
      component.recalculateNumOfDays('2019-05-10', '2019-05-20', true);

      expect(result).toBeDefined();
      expect(result).toEqual(10);
      expect(component.updateNumberOfDays).toHaveBeenCalledWith(10);
    });
  });

  it('updateStartDate should emmit new value', () => {
    let result: MomentInput;
    component.startDateChange.subscribe((newStartDate: MomentInput) => {
      result = newStartDate;
    });
    component.updateStartDate('2019-05-10');

    expect(result).toBeDefined();
    expect(result).toEqual('2019-05-10');
  });

  it('updateEndDate should emmit new value', () => {
    let result: MomentInput;
    component.endDateChange.subscribe((newEndDate: MomentInput) => {
      result = newEndDate;
    });
    component.updateEndDate('2019-05-10');

    expect(result).toBeDefined();
    expect(result).toEqual('2019-05-10');
  });

  it('numberOfDaysChange should emmit new value', () => {
    let result: number;
    component.numberOfDaysChange.subscribe((newNumberOfDays: number) => {
      result = newNumberOfDays;
    });
    component.updateNumberOfDays(20);

    expect(result).toBeDefined();
    expect(result).toEqual(20);
  });
});
