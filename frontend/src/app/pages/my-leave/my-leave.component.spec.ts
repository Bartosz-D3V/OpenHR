import { Injectable } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MdButtonModule, MdMenuModule, MdSelectModule, MdStepperModule, MdToolbarModule } from '@angular/material';

import { CalendarModule } from 'primeng/primeng';

import { Observable } from 'rxjs/Observable';

import { MyLeaveComponent } from './my-leave.component';
import { LeaveApplication } from './domain/leave-application';
import { CapitalizePipe } from '../../shared/pipes/capitalize/capitalize.pipe';
import { DropdownComponent } from '../../shared/components/dropdown/dropdown.component';
import { PageHeaderComponent } from '../../shared/components/page-header/page-header.component';
import { ErrorResolverService } from '../../shared/services/error-resolver/error-resolver.service';

describe('MyLeaveComponent', () => {
  let component: MyLeaveComponent;
  let fixture: ComponentFixture<MyLeaveComponent>;
  const appliedDays = [
    new Date('03/05/2017')
  ];
  const mockLeave = new LeaveApplication();
  mockLeave.subjectId = 1;
  mockLeave.selectedDays = appliedDays;
  mockLeave.leaveType = 'Holiday';
  mockLeave.message = '';

  @Injectable()
  class FakeMyLeaveComponent {
    public getLeaveTypes(): any {
      return Observable.of(null);
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
        MyLeaveComponent,
        PageHeaderComponent,
        CapitalizePipe,
        DropdownComponent,
      ],
      imports: [
        HttpModule,
        FormsModule,
        ReactiveFormsModule,
        MdStepperModule,
        MdButtonModule,
        MdMenuModule,
        MdToolbarModule,
        MdSelectModule,
        CalendarModule,
        NoopAnimationsModule,
      ],
      providers: [
        {
          provide: MyLeaveComponent, useClass: FakeMyLeaveComponent,
        },
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyLeaveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    component.leaveApplication = mockLeave;

    spyOn(console, 'log');
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
