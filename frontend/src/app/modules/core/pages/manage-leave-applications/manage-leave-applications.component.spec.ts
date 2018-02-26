import { Injectable } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import {
  MatCardModule, MatDialogModule, MatIconModule, MatPaginatorModule, MatProgressSpinnerModule, MatSnackBarModule,
  MatTableModule, MatToolbarModule,
} from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { JwtHelperService } from '../../../../shared/services/jwt/jwt-helper.service';
import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
import { InitialsPipe } from '../../../../shared/pipes/initials/initials.pipe';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';
import { CapitalizePipe } from '../../../../shared/pipes/capitalize/capitalize.pipe';
import { NotificationService } from '../../../../shared/services/notification/notification.service';
import { ManageLeaveApplicationsComponent } from './manage-leave-applications.component';
import { ManageLeaveApplicationsService } from './service/manage-leave-applications.service';

describe('ManageLeaveApplicationsComponent', () => {
  let component: ManageLeaveApplicationsComponent;
  let fixture: ComponentFixture<ManageLeaveApplicationsComponent>;

  @Injectable()
  class FakeErrorResolverService {
    public createAlert(error: any): void {
    }
  }

  @Injectable()
  class FakeManageLeaveApplicationsService {
    public getUnacceptedLeaveApplications(managerId: number): Observable<any> {
      return Observable.of(null);
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        ManageLeaveApplicationsComponent,
        InitialsPipe,
        CapitalizePipe,
        PageHeaderComponent,
      ],
      imports: [
        HttpClientTestingModule,
        NoopAnimationsModule,
        MatDialogModule,
        MatCardModule,
        MatIconModule,
        MatToolbarModule,
        MatPaginatorModule,
        MatTableModule,
        MatProgressSpinnerModule,
        MatSnackBarModule,
      ],
      providers: [
        ErrorResolverService,
        JwtHelperService,
        NotificationService,
        {
          provide: ManageLeaveApplicationsService, useClass: FakeManageLeaveApplicationsService,
        },
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageLeaveApplicationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
