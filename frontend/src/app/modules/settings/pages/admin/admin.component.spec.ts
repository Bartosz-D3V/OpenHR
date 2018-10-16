import { Injectable } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Observable } from 'rxjs/Observable';
import {
  MatCardModule,
  MatDatepickerModule,
  MatInputModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatTabsModule,
  MatToolbarModule,
} from '@angular/material';
import { MatMomentDateModule } from '@angular/material-moment-adapter';

import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { AllowanceSettingsComponent } from '@modules/settings/pages/admin/components/allowance-settings/allowance-settings.component';
import { AdminService } from '@modules/settings/pages/admin/service/admin.service';
import { AllowanceSettings } from '@modules/settings/pages/admin/domain/allowance-settings';
import { ResponsiveHelperService } from '@shared/services/responsive-helper/responsive-helper.service';
import { NotificationService } from '@shared/services/notification/notification.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { DisableControlDirective } from '@shared/directives/disable-control/disable-control.directive';
import { ResetControlDirective } from '@shared/directives/reset-control/reset-control.directive';
import { AdminComponent } from './admin.component';

describe('AdminComponent', () => {
  let component: AdminComponent;
  let fixture: ComponentFixture<AdminComponent>;

  @Injectable()
  class FakeAdminService {
    public getAdminAllowanceSettings(): Observable<AllowanceSettings> {
      return Observable.of(new AllowanceSettings());
    }
  }

  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {}
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AdminComponent,
        AllowanceSettingsComponent,
        PageHeaderComponent,
        CapitalizePipe,
        DisableControlDirective,
        ResetControlDirective,
      ],
      imports: [
        ReactiveFormsModule,
        FormsModule,
        HttpClientTestingModule,
        NoopAnimationsModule,
        MatTabsModule,
        MatToolbarModule,
        MatSlideToggleModule,
        MatDatepickerModule,
        MatMomentDateModule,
        MatInputModule,
        MatCardModule,
        MatSnackBarModule,
      ],
      providers: [
        ResponsiveHelperService,
        NotificationService,
        {
          provide: ErrorResolverService,
          useClass: FakeErrorResolverService,
        },
        {
          provide: AdminService,
          useClass: FakeAdminService,
        },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
