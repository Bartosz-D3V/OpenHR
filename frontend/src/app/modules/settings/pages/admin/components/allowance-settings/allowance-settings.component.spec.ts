import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatCardModule, MatDatepickerModule, MatInputModule, MatSlideToggleModule, MatToolbarModule } from '@angular/material';
import { MatMomentDateModule } from '@angular/material-moment-adapter';

import { ResponsiveHelperService } from '@shared/services/responsive-helper/responsive-helper.service';
import { DisableControlDirective } from '@shared/directives/disable-control/disable-control.directive';
import { ResetControlDirective } from '@shared/directives/reset-control/reset-control.directive';
import { AllowanceSettingsComponent } from './allowance-settings.component';

describe('AllowanceSettingsComponent', () => {
  let component: AllowanceSettingsComponent;
  let fixture: ComponentFixture<AllowanceSettingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AllowanceSettingsComponent, DisableControlDirective, ResetControlDirective],
      imports: [
        ReactiveFormsModule,
        FormsModule,
        HttpClientTestingModule,
        NoopAnimationsModule,
        MatSlideToggleModule,
        MatInputModule,
        MatDatepickerModule,
        MatToolbarModule,
        MatCardModule,
        MatMomentDateModule,
      ],
      providers: [ResponsiveHelperService],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllowanceSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
