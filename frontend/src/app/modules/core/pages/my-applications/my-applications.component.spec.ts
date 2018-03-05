import { Injectable } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Observable } from 'rxjs/Observable';
import {
  MatCardModule, MatDialogModule, MatIconModule, MatPaginatorModule, MatProgressSpinnerModule, MatSnackBarModule, MatTableModule,
  MatToolbarModule,
} from '@angular/material';

import { NotificationService } from '@shared/services/notification/notification.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { InitialsPipe } from '@shared/pipes/initials/initials.pipe';
import { LeaveApplicationStatusPipe } from './pipe/leave-application-status.pipe';
import { MyApplicationsService } from './service/my-applications.service';
import { MyApplicationsComponent } from './my-applications.component';

describe('MyApplicationsComponent', () => {
  let component: MyApplicationsComponent;
  let fixture: ComponentFixture<MyApplicationsComponent>;

  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {
    }
  }

  @Injectable()
  class FakeMyApplicationsService {
    public getSubmittedLeaveApplications(subjectId: number): Observable<any> {
      return Observable.of(null);
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        MyApplicationsComponent,
        LeaveApplicationStatusPipe,
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
          provide: MyApplicationsService, useClass: FakeMyApplicationsService,
        },
        {
          provide: ErrorResolverService, useClass: FakeErrorResolverService,
        },
      ],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyApplicationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
