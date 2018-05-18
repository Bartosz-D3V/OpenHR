import { Injectable } from '@angular/core';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatTabsModule } from '@angular/material';

import { AdminComponent } from './admin.component';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { AdminService } from '@modules/settings/pages/admin/service/admin.service';
import { AllowanceSettingsComponent } from '@modules/settings/pages/admin/components/allowance-settings/allowance-settings.component';

describe('AdminComponent', () => {
  let component: AdminComponent;
  let fixture: ComponentFixture<AdminComponent>;

  @Injectable()
  class FakeAdminService {}

  beforeEach(
    async(() => {
      TestBed.configureTestingModule({
        declarations: [AdminComponent, AllowanceSettingsComponent, PageHeaderComponent, CapitalizePipe],
        imports: [HttpClientTestingModule, NoopAnimationsModule, MatTabsModule],
        providers: [
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
