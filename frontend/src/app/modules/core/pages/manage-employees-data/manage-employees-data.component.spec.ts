import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import {
  MatButtonModule, MatCardModule, MatDatepickerModule, MatExpansionModule, MatFormFieldModule, MatIconModule, MatInputModule,
  MatNativeDateModule, MatToolbarModule
} from '@angular/material';

import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { ManageEmployeesDataComponent } from './manage-employees-data.component';

describe('ManageEmployeesDataComponent', () => {
  let component: ManageEmployeesDataComponent;
  let fixture: ComponentFixture<ManageEmployeesDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        ManageEmployeesDataComponent,
        CapitalizePipe,
        PageHeaderComponent,
      ],
      imports: [
        NoopAnimationsModule,
        MatCardModule,
        ReactiveFormsModule,
        FormsModule,
        MatButtonModule,
        MatFormFieldModule,
        MatInputModule,
        MatToolbarModule,
        MatExpansionModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatIconModule,
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageEmployeesDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
