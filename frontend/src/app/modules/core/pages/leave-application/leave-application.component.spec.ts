import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Injectable } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import {
  MatButtonModule, MatInputModule, MatMenuModule, MatSelectModule, MatStepperModule, MatToolbarModule
} from '@angular/material';

import { CalendarModule } from 'primeng/primeng';

import { Observable } from 'rxjs/Observable';

import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
import { LeaveApplicationComponent } from './leave-application.component';
import { LeaveApplication } from './domain/leave-application';
import { LeaveApplicationService } from './service/leave-application.service';

describe('LeaveApplicationComponent', () => {
  let component: LeaveApplicationComponent;
  let fixture: ComponentFixture<LeaveApplicationComponent>;
  const appliedDays = [
    new Date('03/05/2017'),
  ];
  const mockLeave = new LeaveApplication();
  mockLeave.selectedDays = appliedDays;
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
        PageHeaderComponent,
        CapitalizePipe,
      ],
      imports: [
        HttpClientTestingModule,
        FormsModule,
        ReactiveFormsModule,
        MatStepperModule,
        MatButtonModule,
        MatMenuModule,
        MatToolbarModule,
        MatSelectModule,
        MatInputModule,
        CalendarModule,
        NoopAnimationsModule,
      ],
      providers: [
        {
          provide: LeaveApplicationService, useClass: FakeLeaveApplicationService,
        },
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
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

  describe('setEntryDates method', () => {

    it('should set the start day and leave the end date empty if start day is selected and end date is empty', () => {
      component.dateRange = [new Date('06/12/2019')];
      component.setEntryDates();

      expect(component.startDate).toEqual(new Date('06/12/2019'));
      expect(component.endDate).toBeUndefined();
    });

    it('should set the start day and end date if start and end date are selected', () => {
      component.dateRange = [new Date('06/12/2019'), new Date('10/01/2020')];
      component.setEntryDates();

      expect(component.startDate).toEqual(new Date('06/12/2019'));
      expect(component.endDate).toEqual(new Date('10/01/2020'));
    });

  });

  it('clearEntryDates method should set startDay and endDay to null', () => {
    component.startDate = new Date('06/12/2019');
    component.endDate = new Date('10/01/2020');
    component.clearEntryDates();

    expect(component.startDate).toBeNull();
    expect(component.endDate).toBeNull();
  });

  it('clearEndDate should set endDate to null', () => {
    component.startDate = new Date('06/12/2019');
    component.endDate = new Date('10/01/2020');
    component.clearEndDate();

    expect(component.startDate).toEqual(new Date('06/12/2019'));
    expect(component.endDate).toBeNull();
  });

  it('setLeaveDates should set the domain property to selected days', () => {
    component.dateRange = [new Date('06/12/2019'), new Date('10/01/2020')];
    component.setLeaveDates();

    expect(component.leaveApplication.selectedDays).toEqual(component.dateRange);
  });

});
