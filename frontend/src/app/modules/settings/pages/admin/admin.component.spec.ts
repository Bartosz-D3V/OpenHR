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
  MatTabsModule,
  MatToolbarModule,
} from '@angular/material';
import { MatMomentDateModule } from '@angular/material-moment-adapter';

import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { AdminService } from '@modules/settings/pages/admin/service/admin.service';
import { AllowanceSettingsComponent } from '@modules/settings/pages/admin/components/allowance-settings/allowance-settings.component';
import { AllowanceSettings } from '@modules/settings/pages/admin/domain/allowance-settings';
import { AdminComponent } from './admin.component';
import { ResponsiveHelperService } from '@shared/services/responsive-helper/responsive-helper.service';

describe('AdminComponent', () => {
  let component: AdminComponent;
  let fixture: ComponentFixture<AdminComponent>;

  @Injectable()
  class FakeAdminService {
    getAdminAllowanceSettings(): Observable<AllowanceSettings> {
      return Observable.of(new AllowanceSettings());
    }
  }

  beforeEach(
    async(() => {
      TestBed.configureTestingModule({
        declarations: [AdminComponent, AllowanceSettingsComponent, PageHeaderComponent, CapitalizePipe],
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
        ],
        providers: [
          ResponsiveHelperService,
          {
            provide: AdminService,
            useClass: FakeAdminService,
          },
        ],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
