import { Injectable } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Observable } from 'rxjs/Observable';
import {
  MatCardModule,
  MatDialogModule,
  MatIconModule,
  MatPaginatorModule,
  MatProgressSpinnerModule,
  MatSnackBarModule,
  MatTableModule,
  MatToolbarModule,
  MatTooltipModule,
} from '@angular/material';

import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { InitialsPipe } from '@shared/pipes/initials/initials.pipe';
import { ApplicationStatusPipe } from '@modules/core/pages/my-applications/pipe/leave-applicaiton-status/application-status.pipe';
import { ApplicationTypePipe } from '@modules/core/pages/my-applications/pipe/application-type/application-type.pipe';
import { MyApplicationsService } from './service/my-applications.service';
import { MyApplicationsComponent } from './my-applications.component';

describe('MyApplicationsComponent', () => {
  let component: MyApplicationsComponent;
  let fixture: ComponentFixture<MyApplicationsComponent>;

  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {}
  }

  @Injectable()
  class FakeMyApplicationsService {
    public getSubmittedLeaveApplications(subjectId: number): Observable<any> {
      return Observable.of(null);
    }
  }

  beforeEach(
    async(() => {
      TestBed.configureTestingModule({
        declarations: [
          MyApplicationsComponent,
          ApplicationStatusPipe,
          ApplicationTypePipe,
          InitialsPipe,
          CapitalizePipe,
          PageHeaderComponent,
        ],
        imports: [
          RouterTestingModule,
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
          MatTooltipModule,
        ],
        providers: [
          ErrorResolverService,
          JwtHelperService,
          {
            provide: MyApplicationsService,
            useClass: FakeMyApplicationsService,
          },
          {
            provide: ErrorResolverService,
            useClass: FakeErrorResolverService,
          },
        ],
      });
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(MyApplicationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
