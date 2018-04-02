import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ManageDelegationsComponent } from './manage-delegations.component';
import { Injectable } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
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
import { NotificationService } from '@shared/services/notification/notification.service';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { InitialsPipe } from '@shared/pipes/initials/initials.pipe';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { ManageDelegationsService } from '@modules/core/pages/manage-delegations/service/manage-delegations.service';

describe('ManageDelegationsComponent', () => {
  let component: ManageDelegationsComponent;
  let fixture: ComponentFixture<ManageDelegationsComponent>;

  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {}
  }

  @Injectable()
  class FakeManageDelegationsService {}

  beforeEach(
    async(() => {
      TestBed.configureTestingModule({
        declarations: [ManageDelegationsComponent, InitialsPipe, CapitalizePipe, PageHeaderComponent],
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
          MatTooltipModule,
        ],
        providers: [
          ErrorResolverService,
          JwtHelperService,
          NotificationService,
          {
            provide: ManageDelegationsService,
            useClass: FakeManageDelegationsService,
          },
          {
            provide: ErrorResolverService,
            useClass: FakeErrorResolverService,
          },
        ],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageDelegationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
