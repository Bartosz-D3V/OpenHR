import { Injectable } from '@angular/core';
import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AbstractControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { MatDatepickerModule, MatInputModule } from '@angular/material';
import { MomentDateModule } from '@angular/material-moment-adapter';
import { FlexLayoutModule } from '@angular/flex-layout';

import moment = require('moment');
import { MomentInput } from 'moment';

import { DateRangeComponent } from './date-range.component';
import { DateRangeService } from './service/date-range.service';
import { ResponsiveHelperService } from '../../services/responsive-helper/responsive-helper.service';
import { ErrorResolverService } from '../../services/error-resolver/error-resolver.service';
import { BankHoliday } from './domain/bank-holiday/england/bank-holiday';
import { BankHolidayEngland } from './domain/bank-holiday/england/bank-holiday-england';

describe('DateRangeComponent', () => {
  let component: DateRangeComponent;
  let fixture: ComponentFixture<DateRangeComponent>;

  @Injectable()
  class FakeDateRangeService {
  }

  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {
    }

    public createAlert(error: any): void {
    }
  }

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
        HttpClientTestingModule,
        NoopAnimationsModule,
      ],
      providers: [
        ResponsiveHelperService,
        {
          provide: DateRangeService, useClass: FakeDateRangeService,
        },
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
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
      spyOn(component, 'recalculateNumOfDays');
      component.endDate = '2040-02-12';
      startDateCtrl.setValue('2020-02-08');
      component.validateStartDateField();

      expect(startDateCtrl.valid).toBeTruthy();
      expect(component.recalculateNumOfDays).toHaveBeenCalled();
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
      spyOn(component, 'recalculateNumOfDays');
      component.startDate = '2020-02-08';
      endDateCtrl.setValue('2040-02-12');
      component.validateEndDateField();

      expect(endDateCtrl.valid).toBeTruthy();
      expect(component.recalculateNumOfDays).toHaveBeenCalled();
    });
  });

  describe('recalculateNumOfDays', () => {
    it('should set numberOfDays including the end date, but excluding weekends and bank holidays', () => {
      component.recalculateNumOfDays('2019-05-10', '2019-05-20', false);

      expect(component.numberOfDays).toBeDefined();
      expect(component.numberOfDays).toEqual(7);
    });

    it('should set numberOfDays including the end date by default, but excluding weekends and bank holidays', () => {
      component.recalculateNumOfDays('2019-05-10', '2019-05-20');

      expect(component.numberOfDays).toBeDefined();
      expect(component.numberOfDays).toEqual(7);
    });

    it('should set numberOfDays excluding the end date, but excluding weekends and bank holidays', () => {
      component.recalculateNumOfDays('2019-05-10', '2019-05-20', true);

      expect(component.numberOfDays).toBeDefined();
      expect(component.numberOfDays).toEqual(6);
    });

    it('should emmit new value through EventEmmiter', () => {
      let result: number;
      spyOn(component, 'updateNumberOfDays').and.callThrough();
      component.numberOfDaysChange.subscribe((newNumberOfDays: number) => {
        result = newNumberOfDays;
      });
      component.recalculateNumOfDays('2019-05-10', '2019-05-20', true);

      expect(result).toBeDefined();
      expect(result).toEqual(6);
      expect(component.updateNumberOfDays).toHaveBeenCalledWith(6);
    });
  });

  it('updateStartDate should emmit and update new value', () => {
    let result: MomentInput;
    component.startDateChange.subscribe((newStartDate: MomentInput) => {
      result = newStartDate;
    });
    component.updateStartDate('2019-05-10');

    expect(component.startDate).toBeDefined();
    expect(component.startDate).toEqual('2019-05-10');
    expect(result).toBeDefined();
    expect(result).toEqual('2019-05-10');
  });

  it('updateEndDate should emmit and update new value', () => {
    let result: MomentInput;
    component.endDateChange.subscribe((newEndDate: MomentInput) => {
      result = newEndDate;
    });
    component.updateEndDate('2019-05-10');

    expect(component.endDate).toBeDefined();
    expect(component.endDate).toEqual('2019-05-10');
    expect(result).toBeDefined();
    expect(result).toEqual('2019-05-10');
  });

  it('numberOfDaysChange should emmit and update new value', () => {
    let result: number;
    component.numberOfDaysChange.subscribe((newNumberOfDays: number) => {
      result = newNumberOfDays;
    });
    component.updateNumberOfDays(20);

    expect(component.numberOfDays).toBeDefined();
    expect(component.numberOfDays).toEqual(20);
    expect(result).toBeDefined();
    expect(result).toEqual(20);
  });

  describe('isBankHoliday', () => {
    let mockBankHolidaysEngland: BankHolidayEngland;
    let mockEvents: Array<BankHoliday>;
    const mockBankHoliday1: BankHoliday = new BankHoliday();
    const mockBankHoliday2: BankHoliday = new BankHoliday();

    beforeEach(() => {
      mockBankHoliday1.date = '2020-01-01';
      mockBankHoliday2.date = '2020-12-31';
      mockEvents = [];
      mockEvents.push(mockBankHoliday1);
      mockEvents.push(mockBankHoliday2);
      mockBankHolidaysEngland = new BankHolidayEngland('England-and-Wales', mockEvents);
    });

    it('should return true if the given date is a bank holiday', () => {
      component.bankHolidaysEngland = mockBankHolidaysEngland;

      expect(component.isBankHoliday(moment('2020-01-01'))).toBeTruthy();
    });

    it('should return false if the given date is not a bank holiday', () => {
      component.bankHolidaysEngland = mockBankHolidaysEngland;

      expect(component.isBankHoliday(moment('2020-01-11'))).toBeFalsy();
    });
  });

  describe('isMobile', () => {
    it('should return true if screen is less than 480px', inject([ResponsiveHelperService],
      (service: ResponsiveHelperService) => {
        component['_responsiveHelper'] = service;
        spyOn(component['_responsiveHelper'], 'isMobile').and.returnValue(true);

        expect(component.isMobile()).toBeTruthy();
      }));

    it('should return false if screen is greater than 480px', inject([ResponsiveHelperService],
      (service: ResponsiveHelperService) => {
        component['_responsiveHelper'] = service;
        spyOn(component['_responsiveHelper'], 'isMobile').and.returnValue(false);

        expect(component.isMobile()).toBeFalsy();
      }));
  });
});
