import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { Injectable } from '@angular/core';
import { AbstractControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatCardModule,
  MatDatepickerModule,
  MatInputModule,
  MatMenuModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatSelectModule,
  MatSnackBarModule,
  MatStepperModule,
  MatToolbarModule,
} from '@angular/material';
import { MomentDateModule } from '@angular/material-moment-adapter';
import { MomentInput } from 'moment';
import { FlexLayoutModule } from '@angular/flex-layout';
import { Observable } from 'rxjs/Observable';

import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { DateRangeComponent } from '@shared/components/date-range/date-range.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { ResponsiveHelperService } from '@shared/services/responsive-helper/responsive-helper.service';
import { NotificationService } from '@shared/services/notification/notification.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { LeaveApplication } from '@shared/domain/application/leave-application';
import { LeaveType } from '@shared/domain/application/leave-type';
import { LeaveApplicationService } from './service/leave-application.service';
import { LeaveApplicationComponent } from './leave-application.component';

describe('LeaveApplicationComponent', () => {
  let component: LeaveApplicationComponent;
  let fixture: ComponentFixture<LeaveApplicationComponent>;
  const mockStartDate: MomentInput = '2020-05-05';
  const mockEndDate: MomentInput = '2020-05-10';
  const mockLeave = new LeaveApplication(null, mockStartDate, mockEndDate, false, false, false, null, null);

  @Injectable()
  class FakeLeaveApplicationService {
    public getLeaveTypes(): Observable<Array<string>> {
      return Observable.of(['Holiday']);
    }
  }

  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {}
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LeaveApplicationComponent, DateRangeComponent, PageHeaderComponent, CapitalizePipe],
      imports: [
        HttpClientTestingModule,
        NoopAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        FlexLayoutModule,
        MatDatepickerModule,
        MomentDateModule,
        MatStepperModule,
        MatButtonModule,
        MatMenuModule,
        MatToolbarModule,
        MatSelectModule,
        MatInputModule,
        MatRadioModule,
        MatCardModule,
        MatSnackBarModule,
        MatProgressSpinnerModule,
      ],
      providers: [
        JwtHelperService,
        NotificationService,
        {
          provide: LeaveApplicationService,
          useClass: FakeLeaveApplicationService,
        },
        {
          provide: ErrorResolverService,
          useClass: FakeErrorResolverService,
        },
        ResponsiveHelperService,
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeaveApplicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    component.leaveApplication = mockLeave;
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('ngOnInit method should trigger service and instantiate domain object', () => {
    spyOn(component, 'getLeaveTypes');
    component.ngOnInit();

    expect(component.getLeaveTypes).toHaveBeenCalled();
    expect(component.leaveApplication).toBeDefined();
  });

  it('setStartDate should set the start date in domain object and itself', () => {
    spyOn(component, 'filterComingBankHolidays');
    component.setStartDate(mockStartDate);

    expect(component.leaveApplication.startDate).toBeDefined();
    expect(component.leaveApplication.startDate).toEqual(mockStartDate);
  });

  it('setEndDate should set the end date in domain object and itself', () => {
    spyOn(component, 'filterComingBankHolidays');
    component.setEndDate(mockEndDate);

    expect(component.leaveApplication.endDate).toBeDefined();
    expect(component.leaveApplication.endDate).toEqual(mockEndDate);
  });

  it('setLeaveType should update selector type', () => {
    const mockLeaveType: LeaveType = new LeaveType(1, 'Holiday', 'Annual leave');
    component.leaveTypes = [mockLeaveType];
    component.setLeaveType('Holiday');

    expect(component.leaveApplication.leaveType).toBeDefined();
    expect(typeof component.leaveApplication.leaveType.leaveCategory).toBe('string');
    expect(component.leaveApplication.leaveType.leaveTypeId).toEqual(1);
    expect(component.leaveApplication.leaveType.leaveCategory).toEqual('Holiday');
    expect(component.leaveApplication.leaveType.description).toEqual('Annual leave');
  });

  describe('leaveApplication Form Group', () => {
    describe('leaveTypeSelectorController', () => {
      let leaveTypeCtrl: AbstractControl;

      beforeEach(() => {
        leaveTypeCtrl = component.leaveApplicationFormGroup.controls['leaveTypeSelectorController'];
      });

      it('should be marked invalid if it is empty', () => {
        leaveTypeCtrl.setValue(null);

        expect(leaveTypeCtrl.valid).toBeFalsy();
      });

      it('should be marked valid if it is not empty', () => {
        leaveTypeCtrl.setValue('range');

        expect(leaveTypeCtrl.valid).toBeTruthy();
      });
    });

    describe('messageController', () => {
      let messageCtrl: AbstractControl;

      beforeEach(() => {
        messageCtrl = component.leaveApplicationFormGroup.controls['messageController'];
      });

      it('should be marked invalid if it exceed 500 characters', () => {
        messageCtrl.setValue('X'.repeat(501));

        expect(messageCtrl.valid).toBeFalsy();
      });

      it('should be marked valid if it is not exceeding 500 chars', () => {
        messageCtrl.setValue('Some message');

        expect(messageCtrl.valid).toBeTruthy();
      });
    });
  });

  describe('isMobile method', () => {
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
