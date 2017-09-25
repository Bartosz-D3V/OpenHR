import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MdStepperModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { MyLeaveComponent } from './my-leave.component';
import { CapitalizePipe } from '../../shared/pipes/capitalize/capitalize.pipe';
import { PageHeaderComponent } from '../../shared/components/page-header/page-header.component';

describe('MyLeaveComponent', () => {
  let component: MyLeaveComponent;
  let fixture: ComponentFixture<MyLeaveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        MyLeaveComponent,
        PageHeaderComponent,
        CapitalizePipe,
      ],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        MdStepperModule,
        NoopAnimationsModule,
      ],
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
