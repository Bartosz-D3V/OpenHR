import { async, ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { Injectable } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import {
  MatButtonModule, MatDatepickerModule, MatInputModule, MatMenuModule, MatSelectModule, MatStepperModule, MatToolbarModule
} from '@angular/material';
import { MomentDateModule } from '@angular/material-moment-adapter';

import { Observable } from 'rxjs/Observable';

import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { DateRangeComponent } from '../../../../shared/components/date-range/date-range.component';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
import { LeaveApplicationComponent } from './leave-application.component';
import { LeaveApplication } from './domain/leave-application';
import { LeaveApplicationService } from './service/leave-application.service';
import { MomentInput } from 'moment';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ResponsiveHelperService } from '../../../../shared/services/responsive-helper/responsive-helper.service';

describe('LeaveApplicationComponent', () => {
  let component: LeaveApplicationComponent;
  let fixture: ComponentFixture<LeaveApplicationComponent>;
  const mockStartDate: MomentInput = '2020-05-05';
  const mockEndDate: MomentInput = '2020-05-10';
  const mockLeave = new LeaveApplication();
  mockLeave.leaveType = 'Holiday';
  mockLeave.message = '';

  @Injectable()
  class FakeLeaveApplicationService {
    public getLeaveTypes(): Observable<Array<string>> {
      return Observable.of(['Holiday']);
    }
  }

  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        LeaveApplicationComponent,
        DateRangeComponent,
        PageHeaderComponent,
        CapitalizePipe,
      ],
      imports: [
        HttpClientTestingModule,
        FormsModule,
        FlexLayoutModule,
        MatDatepickerModule,
        ReactiveFormsModule,
        MomentDateModule,
        MatStepperModule,
        MatButtonModule,
        MatMenuModule,
        MatToolbarModule,
        MatSelectModule,
        MatInputModule,
        NoopAnimationsModule,
      ],
      providers: [
        {
          provide: LeaveApplicationService, useClass: FakeLeaveApplicationService,
        },
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
        ResponsiveHelperService,
      ],
    })
      .compileComponents();
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
    component.setStartDate(mockStartDate);

    expect(component.leaveApplication.startDate).toBeDefined();
    expect(component.leaveApplication.startDate).toEqual(mockStartDate);
  });

  it('setEndDate should set the end date in domain object and itself', () => {
    component.setEndDate(mockEndDate);

    expect(component.leaveApplication.endDate).toBeDefined();
    expect(component.leaveApplication.endDate).toEqual(mockEndDate);
  });

  it('isMobile should return true if screen is less than 480px', inject([ResponsiveHelperService],
    (service: ResponsiveHelperService) => {
      component['_responsiveHelper'] = service;
      spyOn(component['_responsiveHelper'], 'isMobile').and.returnValue(true);

      expect(component.isMobile()).toBeTruthy();
    }));

  it('isMobile should return false if screen is greater than 480px', inject([ResponsiveHelperService],
    (service: ResponsiveHelperService) => {
      component['_responsiveHelper'] = service;
      spyOn(component['_responsiveHelper'], 'isMobile').and.returnValue(false);

      expect(component.isMobile()).toBeFalsy();
    }));
});
