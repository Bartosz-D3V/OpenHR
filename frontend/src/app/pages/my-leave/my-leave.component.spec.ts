import { Http, HttpModule } from '@angular/http';

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MdButtonModule, MdMenuModule, MdStepperModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { MyLeaveComponent } from './my-leave.component';
import { CapitalizePipe } from '../../shared/pipes/capitalize/capitalize.pipe';
import { PageHeaderComponent } from '../../shared/components/page-header/page-header.component';
import { DropdownComponent } from '../../shared/components/dropdown/dropdown.component';
import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';

describe('MyLeaveComponent', () => {
  let component: MyLeaveComponent;
  let fixture: ComponentFixture<MyLeaveComponent>;

  @Injectable()
  class FakeMyLeaveComponent {
    public getLeaveTypes(): any {
      return Observable.of(null);
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
        NoopAnimationsModule,
      ],
      providers: [
        {
          provide: MyLeaveComponent, useClass: FakeMyLeaveComponent,
        },
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyLeaveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
