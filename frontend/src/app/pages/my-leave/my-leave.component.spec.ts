import { Injectable } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MdButtonModule, MdMenuModule, MdStepperModule } from '@angular/material';

import { Observable } from 'rxjs/Observable';

import { MyLeaveComponent } from './my-leave.component';
import { CapitalizePipe } from '../../shared/pipes/capitalize/capitalize.pipe';
import { DropdownComponent } from '../../shared/components/dropdown/dropdown.component';
import { PageHeaderComponent } from '../../shared/components/page-header/page-header.component';
import { ErrorResolverService } from '../../shared/services/error-resolver/error-resolver.service';

describe('MyLeaveComponent', () => {
  let component: MyLeaveComponent;
  let fixture: ComponentFixture<MyLeaveComponent>;

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

    spyOn(console, 'log');
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

});
