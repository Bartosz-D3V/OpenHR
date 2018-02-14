import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material';

import { JwtHelperService } from '../../../../shared/services/jwt/jwt-helper.service';
import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
import { ManageLeaveApplicationsComponent } from './manage-leave-applications.component';
import { ManageLeaveApplicationsService } from './service/manage-leave-applications.service';

describe('ManageLeaveApplicationsComponent', () => {
  let component: ManageLeaveApplicationsComponent;
  let fixture: ComponentFixture<ManageLeaveApplicationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        ManageLeaveApplicationsComponent,
      ],
      imports: [
        MatDialogModule,
      ],
      providers: [
        ErrorResolverService,
        JwtHelperService,
        ManageLeaveApplicationsService,
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
