import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyDetailsComponent } from './my-details.component';
import { PageHeaderComponent } from '../../shared/components/page-header/page-header.component';
import { MdDatepickerModule, MdExpansionModule, MdNativeDateModule, MdToolbarModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CapitalizePipe } from '../../shared/pipes/capitalize/capitalize.pipe';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('MyDetailsComponent', () => {
  let component: MyDetailsComponent;
  let fixture: ComponentFixture<MyDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        MyDetailsComponent,
        PageHeaderComponent,
        CapitalizePipe,
      ],
      imports: [
        MdToolbarModule,
        FormsModule,
        ReactiveFormsModule,
        MdToolbarModule,
        MdExpansionModule,
        MdDatepickerModule,
        MdNativeDateModule,
        NoopAnimationsModule,
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
