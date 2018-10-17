import { Injectable } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';
import {
  MatCardModule,
  MatDialog,
  MatDialogModule,
  MatIconModule,
  MatInputModule,
  MatPaginatorModule,
  MatProgressSpinnerModule,
  MatSnackBarModule,
  MatTableModule,
  MatToolbarModule,
  MatTooltipModule,
} from '@angular/material';
import { Observable } from 'rxjs/Observable';
import { _throw } from 'rxjs/observable/throw';
import { MomentInput } from 'moment';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { InitialsPipe } from '@shared/pipes/initials/initials.pipe';
import { PageHeaderComponent } from '@shared/components/page-header/page-header.component';
import { CapitalizePipe } from '@shared/pipes/capitalize/capitalize.pipe';
import { NotificationService } from '@shared/services/notification/notification.service';
import { LeaveApplication } from '@shared/domain/application/leave-application';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';
import { InputModalComponent } from '@shared/components/input-modal/input-modal.component';
import { ManageLeaveApplicationsComponent } from './manage-leave-applications.component';
import { ManageLeaveApplicationsService } from './service/manage-leave-applications.service';

describe('ManageLeaveApplicationsComponent', () => {
  let component: ManageLeaveApplicationsComponent;
  let fixture: ComponentFixture<ManageLeaveApplicationsComponent>;
  const mockStartDate: MomentInput = '2020-05-05';
  const mockEndDate: MomentInput = '2020-05-10';
  const mockLeave = new LeaveApplication(null, mockStartDate, mockEndDate, false, false, false, null, null);

  @Injectable()
  class FakeErrorResolverService {
    public handleError(error: any): void {}
  }

  @Injectable()
  class FakeManageLeaveApplicationsService {
    public getUnacceptedLeaveApplications(managerId: number): Observable<any> {
      return Observable.of(null);
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ManageLeaveApplicationsComponent, InitialsPipe, CapitalizePipe, PageHeaderComponent, InputModalComponent],
      imports: [
        HttpClientTestingModule,
        NoopAnimationsModule,
        FormsModule,
        MatInputModule,
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
          provide: ManageLeaveApplicationsService,
          useClass: FakeManageLeaveApplicationsService,
        },
        {
          provide: ErrorResolverService,
          useClass: FakeErrorResolverService,
        },
        MatDialog,
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageLeaveApplicationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('fetchLeaveApplications method', () => {
    it('should set loading to true', () => {
      spyOn(component['_manageLeaveApplicationsService'], 'getAwaitingForActionLeaveApplications').and.returnValue(Observable.of({}));

      expect(component.isFetching).toBeTruthy();
      component.fetchLeaveApplications();
      expect(component.isFetching).toBeFalsy();
    });

    it('should set leave applications field', () => {
      spyOn(component['_manageLeaveApplicationsService'], 'getAwaitingForActionLeaveApplications').and.returnValue(
        Observable.of([mockLeave])
      );
      component.fetchLeaveApplications();

      expect(component.leaveApplications).toEqual([mockLeave]);
    });

    it('should set the dataSource', () => {
      spyOn(component['_manageLeaveApplicationsService'], 'getAwaitingForActionLeaveApplications').and.returnValue(
        Observable.of([mockLeave])
      );
      component.fetchLeaveApplications();

      expect(component.dataSource).toBeDefined();
    });

    it('should set the results length field', () => {
      spyOn(component['_manageLeaveApplicationsService'], 'getAwaitingForActionLeaveApplications').and.returnValue(
        Observable.of([mockLeave])
      );
      component.fetchLeaveApplications();

      expect(component.resultsLength).toBe(1);
    });

    it('should call errorResolver in case of an error', () => {
      spyOn(component['_errorResolver'], 'handleError');
      spyOn(component['_manageLeaveApplicationsService'], 'getAwaitingForActionLeaveApplications').and.returnValue(_throw('Error'));
      component.fetchLeaveApplications();

      expect(component['_errorResolver'].handleError).toHaveBeenCalled();
    });
  });

  describe('approveLeaveApplication method', () => {
    it('should invoke service method using process instance id', () => {
      spyOn(component['_manageLeaveApplicationsService'], 'approveLeaveApplicationByManager').and.returnValue(Observable.of({}));
      component.approveLeaveApplication('11AZ');

      expect(component['_manageLeaveApplicationsService'].approveLeaveApplicationByManager).toHaveBeenCalledWith('11AZ');
    });

    it('should open snackBar notification with success message', () => {
      spyOn(component['_manageLeaveApplicationsService'], 'approveLeaveApplicationByManager').and.returnValue(Observable.of({}));
      spyOn(component['_notificationService'], 'openSnackBar');
      component.approveLeaveApplication('11AZ');

      expect(component['_notificationService'].openSnackBar).toHaveBeenCalledWith('Application has been accepted', 'OK');
    });

    it('should call errorResolver in case of an error', () => {
      spyOn(component['_errorResolver'], 'handleError');
      spyOn(component['_manageLeaveApplicationsService'], 'approveLeaveApplicationByManager').and.returnValue(_throw('Error'));
      component.approveLeaveApplication('12A');

      expect(component['_errorResolver'].handleError).toHaveBeenCalled();
    });
  });

  describe('rejectLeaveApplication method', () => {
    it('should invoke service method using process instance id', () => {
      spyOn<any>(component, 'openDialog').and.returnValue(Observable.of({ refusalReason: null, cancelled: false }));
      spyOn(component['_manageLeaveApplicationsService'], 'rejectLeaveApplicationByManager').and.returnValue(Observable.of({}));
      component.rejectLeaveApplication('11AZ');

      expect(component['_manageLeaveApplicationsService'].rejectLeaveApplicationByManager).toHaveBeenCalledWith('11AZ', null);
    });

    it('should open snackBar notification with success message', () => {
      spyOn<any>(component, 'openDialog').and.returnValue(Observable.of({ refusalReason: null, cancelled: false }));
      spyOn(component['_manageLeaveApplicationsService'], 'rejectLeaveApplicationByManager').and.returnValue(Observable.of({}));
      spyOn(component['_notificationService'], 'openSnackBar');
      component.rejectLeaveApplication('11AZ');

      expect(component['_notificationService'].openSnackBar).toHaveBeenCalledWith('Application has been rejected', 'OK');
    });

    it('should call errorResolver in case of an error', () => {
      spyOn<any>(component, 'openDialog').and.returnValue(Observable.of({ refusalReason: null, cancelled: false }));
      spyOn(component['_errorResolver'], 'handleError');
      spyOn(component['_manageLeaveApplicationsService'], 'rejectLeaveApplicationByManager').and.returnValue(_throw('Error'));
      component.rejectLeaveApplication('12A');

      expect(component['_errorResolver'].handleError).toHaveBeenCalled();
    });

    it('should do nothing if cancelled in pop-up window (modal)', () => {
      spyOn<any>(component, 'openDialog').and.returnValue(Observable.of({ refusalReason: null, cancelled: true }));
      spyOn(component['_manageLeaveApplicationsService'], 'rejectLeaveApplicationByManager');
      spyOn(component['_notificationService'], 'openSnackBar');
      spyOn(component['_errorResolver'], 'handleError');
      component.rejectLeaveApplication('12A');

      expect(component['_manageLeaveApplicationsService'].rejectLeaveApplicationByManager).not.toHaveBeenCalled();
      expect(component['_notificationService'].openSnackBar).not.toHaveBeenCalled();
      expect(component['_errorResolver'].handleError).not.toHaveBeenCalled();
    });
  });

  describe('getSubjectFullName method', () => {
    it('should fetch lightweight subject if it is not present in the map', () => {
      component['subjects'].clear();
      spyOn(component['_lightweightSubjectService'], 'getUser').and.returnValue(
        Observable.of(new LightweightSubject(1, 'Mikolaj', 'Kopernik', 'Astronomist'))
      );
      const subject: LightweightSubject = component.getSubjectFullName(1);

      expect(component['subjects'].has('1')).toBeTruthy();
      expect(subject).toBeDefined();
      expect(subject.lastName).toEqual('Kopernik');
    });

    it('should return lightweight subject from map if found', () => {
      component['subjects'].set('3', new LightweightSubject(3, 'Mikolaj', 'Kopernik', 'Astronomist'));
      const subject: LightweightSubject = component.getSubjectFullName(3);

      expect(component['subjects'].has('3')).toBeTruthy();
      expect(subject).toBeDefined();
      expect(subject.lastName).toEqual('Kopernik');
    });
  });
});
