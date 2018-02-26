import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { ISubscription } from 'rxjs/Subscription';

import { LeaveApplication } from '../../../../shared/domain/leave-application/leave-application';
import { JwtHelperService } from '../../../../shared/services/jwt/jwt-helper.service';
import { ManageLeaveApplicationsService } from './service/manage-leave-applications.service';
import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';

@Component({
  selector: 'app-manage-leave-applications',
  templateUrl: './manage-leave-applications.component.html',
  styleUrls: ['./manage-leave-applications.component.scss'],
  providers: [
    ManageLeaveApplicationsService,
    JwtHelperService,
  ],
})
export class ManageLeaveApplicationsComponent implements OnInit, OnDestroy {

  @ViewChild(MatPaginator)
  private paginator: MatPaginator;

  private $leaveApplications: ISubscription;
  leaveApplications: Array<LeaveApplication>;
  isLoadingResults: boolean;
  displayedColumns: Array<string> = ['applicationId', 'from', 'to', 'employeeName', 'reject', 'approve'];
  resultsLength = 0;

  dataSource: MatTableDataSource<LeaveApplication>;

  constructor(private _manageLeaveApplicationsService: ManageLeaveApplicationsService,
              private _jwtHelper: JwtHelperService,
              private _errorResolver: ErrorResolverService) {
  }

  ngOnInit() {
    this.isLoadingResults = true;
    this.fetchLeaveApplications();
  }

  ngOnDestroy(): void {
    this.$leaveApplications.unsubscribe();
  }

  private fetchLeaveApplications(): void {
    this.$leaveApplications = this._manageLeaveApplicationsService
      .getAwaitingForManagerLeaveApplications(this._jwtHelper.getSubjectId())
      .subscribe((result: Array<LeaveApplication>) => {
          this.leaveApplications = result;
          this.dataSource = new MatTableDataSource<LeaveApplication>(result);
          this.dataSource.paginator = this.paginator;
          this.isLoadingResults = false;
          this.resultsLength = result.length;
        },
        (error: any) => {
          this._errorResolver.handleError(error);
        });
  }

  public approveLeaveApplication(processInstanceId: string): void {
    this._leaveApplicationService
      .approveLeaveApplicationByManager(processInstanceId)
      .subscribe(() => {
        this.fetchLeaveApplications();
        const message = 'Application has been accepted';
        this._notificationService.openSnackBar(message, 'OK');
      }, (error: any) => {
        this._errorResolver.createAlert(error);
      });
  }

  public rejectLeaveApplication(processInstanceId: string): void {
    this._leaveApplicationService
      .rejectLeaveApplicationByManager(processInstanceId)
      .subscribe(() => {
        this.fetchLeaveApplications();
        const message = 'Application has been rejected';
        this._notificationService.openSnackBar(message, 'OK');
      }, (error: any) => {
        this._errorResolver.createAlert(error);
      });
  }
}
