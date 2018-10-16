import { Injectable } from '@angular/core';
import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { AbstractControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatDatepickerModule, MatInputModule } from '@angular/material';
import { MomentDateModule } from '@angular/material-moment-adapter';
import { FlexLayoutModule } from '@angular/flex-layout';
import * as moment from 'moment';

import { ResponsiveHelperService } from '../../services/responsive-helper/responsive-helper.service';
import { ErrorResolverService } from '../../services/error-resolver/error-resolver.service';
import { JwtHelperService } from '../../services/jwt/jwt-helper.service';
import { BankHoliday } from './domain/bank-holiday/england/bank-holiday';
import { BankHolidayEngland } from './domain/bank-holiday/england/bank-holiday-england';
import { DateRangeComponent } from './date-range.component';
import { DateRangeService } from './service/date-range.service';

describe('DateRangeComponent', () => {
  let component: DateRangeComponent;
  let fixture: ComponentFixture<DateRangeComponent>;

  @Injectable()
  class FakeDateRangeService {}

  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {}
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DateRangeComponent],
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
        JwtHelperService,
        {
          provide: DateRangeService,
          useClass: FakeDateRangeService,
        },
        {
          provide: ErrorResolverService,
          useClass: FakeErrorResolverService,
        },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DateRangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should not display description by default', () => {
    expect(component.showDescription).toBeFalsy();
  });

  describe('date range form group', () => {
    describe('startDate controller', () => {
      let startDateCtrl: AbstractControl;

      beforeEach(() => {
        startDateCtrl = component.dateRange.get('startDate');
      });

      it('should be marked invalid if input is empty', () => {
        component.requireStartDate = true;
        startDateCtrl.setValue(null);

        expect(startDateCtrl.valid).toBeFalsy();
      });

      it('should be marked valid if input is empty, but requireStartDate is set to false', () => {
        component.requireStartDate = false;
        component.buildForm();
        startDateCtrl = component.dateRange.get('startDate');
        startDateCtrl.setValue(null);

        expect(startDateCtrl.valid).toBeTruthy();
      });

      it('should be marked valid if input is not empty', () => {
        startDateCtrl.setValue(moment([2017, 0, 1]));

        expect(startDateCtrl.valid).toBeTruthy();
      });
    });

    describe('endDate controller', () => {
      let endDateCtrl: AbstractControl;

      beforeEach(() => {
        endDateCtrl = component.dateRange.get('endDate');
      });

      it('should be marked invalid if input is empty', () => {
        component.requireEndDate = true;
        endDateCtrl.setValue(null);

        expect(endDateCtrl.valid).toBeFalsy();
      });

      it('should be marked valid if input is empty, but requireEndDate is set to false', () => {
        component.requireEndDate = false;
        component.buildForm();
        endDateCtrl = component.dateRange.get('endDate');
        endDateCtrl.setValue(null);

        expect(endDateCtrl.valid).toBeTruthy();
      });

      it('should be marked valid if input is not empty', () => {
        endDateCtrl.setValue(moment([2017, 5, 1]));

        expect(endDateCtrl.valid).toBeTruthy();
      });
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

    it('should set numberOfDays excluding the end date and excluding weekends and bank holidays', () => {
      component.recalculateNumOfDays('2019-05-10', '2019-05-20', true);

      expect(component.numberOfDays).toBeDefined();
      expect(component.numberOfDays).toEqual(6);
    });

    it('should set numberOfDays to 2 if the end date and start date are the same and end date is not excluded', () => {
      component.recalculateNumOfDays('2019-05-10', '2019-05-10', false);

      expect(component.numberOfDays).toBeDefined();
      expect(component.numberOfDays).toEqual(1);
    });

    it('should set numberOfDays to 1 if the end date and start date are the same and end date is excluded', () => {
      component.recalculateNumOfDays('2019-05-10', '2019-05-10', true);

      expect(component.numberOfDays).toBeDefined();
      expect(component.numberOfDays).toEqual(0);
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
    let result: moment.MomentInput;
    component.startDateChange.subscribe((newStartDate: moment.MomentInput) => {
      result = newStartDate;
    });
    component.updateStartDate('2019-05-10');

    expect(component.startDate).toBeDefined();
    expect(component.startDate).toEqual('2019-05-10');
    expect(result).toBeDefined();
    expect(result).toEqual('2019-05-10');
  });

  it('updateEndDate should emmit and update new value', () => {
    let result: moment.MomentInput;
    component.endDateChange.subscribe((newEndDate: moment.MomentInput) => {
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

  describe('reset method', () => {
    let startDateCtrl: AbstractControl;
    let endDateCtrl: AbstractControl;

    beforeEach(() => {
      startDateCtrl = component.dateRange.controls['startDate'];
      endDateCtrl = component.dateRange.controls['endDate'];
    });

    afterEach(() => {
      startDateCtrl.reset();
      endDateCtrl.reset();
    });

    it('should reset all values', () => {
      startDateCtrl.setValue('2020-01-11');
      endDateCtrl.setValue('2020-01-20');
      component.reset();

      expect(startDateCtrl.value).toBeNull();
      expect(endDateCtrl.value).toBeNull();
    });
  });

  describe('isMobile', () => {
    it('should return true if screen is less than 480px', inject([ResponsiveHelperService], (service: ResponsiveHelperService) => {
      component['_responsiveHelper'] = service;
      spyOn(component['_responsiveHelper'], 'isMobile').and.returnValue(true);

      expect(component.isMobile()).toBeTruthy();
    }));

    it('should return false if screen is greater than 480px', inject([ResponsiveHelperService], (service: ResponsiveHelperService) => {
      component['_responsiveHelper'] = service;
      spyOn(component['_responsiveHelper'], 'isMobile').and.returnValue(false);

      expect(component.isMobile()).toBeFalsy();
    }));
  });
});
