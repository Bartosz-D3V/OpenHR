import { Injectable } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MdButtonModule, MdMenuModule, MdSelectModule, MdStepperModule, MdToolbarModule } from '@angular/material';

import { CalendarModule } from 'primeng/primeng';

import { Observable } from 'rxjs/Observable';

import { MyLeaveComponent } from './my-leave.component';
import { CapitalizePipe } from '../../shared/pipes/capitalize/capitalize.pipe';
import { DropdownComponent } from '../../shared/components/dropdown/dropdown.component';
import { PageHeaderComponent } from '../../shared/components/page-header/page-header.component';
import { ErrorResolverService } from '../../shared/services/error-resolver/error-resolver.service';
import { Leave } from './domain/leave';

describe('MyLeaveComponent', () => {
  let component: MyLeaveComponent;
  let fixture: ComponentFixture<MyLeaveComponent>;
  const appliedDays = [
    new Date('03/05/2017')
  ];
  const mockLeave = new Leave();
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
    component.leave = mockLeave;

    spyOn(console, 'log');
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

});
