import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { ISubscription } from 'rxjs/Subscription';

import { LeaveApplication } from '@shared/domain/application/leave-application';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { NotificationService } from '@shared/services/notification/notification.service';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';
import { LightweightSubjectService } from '@modules/core/core-wrapper/service/lightweight-subject.service';
import { ManageLeaveApplicationsService } from './service/manage-leave-applications.service';

@Component({
  selector: 'app-manage-leave-applications',
  templateUrl: './manage-leave-applications.component.html',
  styleUrls: ['./manage-leave-applications.component.scss'],
  providers: [ManageLeaveApplicationsService, LightweightSubjectService, JwtHelperService, NotificationService],
})
export class ManageLeaveApplicationsComponent implements OnInit, OnDestroy {
  private $leaveApplications: ISubscription;
  private $subjects: ISubscription;
  private subjects: Map<string, LightweightSubject> = new Map<string, LightweightSubject>();
  public leaveApplications: Array<LeaveApplication>;
  public isLoadingResults: boolean;
  public displayedColumns: Array<string> = ['applicationId', 'from', 'to', 'employeeName', 'info', 'reject', 'approve'];
  public resultsLength = 0;
  public dataSource: MatTableDataSource<LeaveApplication>;

  @ViewChild(MatPaginator) private paginator: MatPaginator;

  constructor(
    private _manageLeaveApplicationsService: ManageLeaveApplicationsService,
    private _lightweightSubjectService: LightweightSubjectService,
    private _jwtHelper: JwtHelperService,
    private _notificationService: NotificationService,
    private _errorResolver: ErrorResolverService
  ) {}

  ngOnInit() {
    this.isLoadingResults = true;
    this.fetchLeaveApplications();
  }

  ngOnDestroy(): void {
    this.unsubscribeAll();
  }

  private unsubscribeAll(): void {
    if (this.$leaveApplications) {
      this.$leaveApplications.unsubscribe();
    }
    if (this.$subjects) {
      this.$subjects.unsubscribe();
    }
  }

  public fetchLeaveApplications(): void {
    this.isLoadingResults = true;
    this.$leaveApplications = this._manageLeaveApplicationsService
      .getAwaitingForActionLeaveApplications(this._jwtHelper.getSubjectId())
      .subscribe(
        (result: Array<LeaveApplication>) => {
          this.leaveApplications = result;
          this.dataSource = new MatTableDataSource<LeaveApplication>(result);
          this.dataSource.paginator = this.paginator;
          this.isLoadingResults = false;
          this.resultsLength = result.length;
        },
        (httpErrorResponse: HttpErrorResponse) => {
          this._errorResolver.handleError(httpErrorResponse.error);
        }
      );
  }

  public approveLeaveApplication(processInstanceId: string): void {
    this._manageLeaveApplicationsService.approveLeaveApplicationByManager(processInstanceId).subscribe(
      () => {
        this.fetchLeaveApplications();
        const message = 'Application has been accepted';
        this._notificationService.openSnackBar(message, 'OK');
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public rejectLeaveApplication(processInstanceId: string): void {
    this._manageLeaveApplicationsService.rejectLeaveApplicationByManager(processInstanceId).subscribe(
      () => {
        this.fetchLeaveApplications();
        const message = 'Application has been rejected';
        this._notificationService.openSnackBar(message, 'OK');
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public getSubjectFullName(subjectId: number): LightweightSubject {
    if (!this.subjects.has(subjectId.toString())) {
      this.$subjects = this._lightweightSubjectService.getUser(subjectId).subscribe((res: LightweightSubject) => {
        this.subjects.set(subjectId.toString(), res);
        return res;
      });
    }
    return this.subjects.get(subjectId.toString());
  }
}
