import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { ISubscription } from 'rxjs/Subscription';

import { LeaveApplication } from '../../../../shared/domain/leave-application/leave-application';
import { NotificationService } from '../../../../shared/services/notification/notification.service';
import { JwtHelperService } from '../../../../shared/services/jwt/jwt-helper.service';
import { ErrorResolverService } from '../../../../shared/services/error-resolver/error-resolver.service';
import { MyApplicationsService } from './service/my-applications.service';

@Component({
  selector: 'app-my-applications',
  templateUrl: './my-applications.component.html',
  styleUrls: ['./my-applications.component.scss'],
  providers: [
    MyApplicationsService,
    JwtHelperService,
    NotificationService,
  ],
})
export class MyApplicationsComponent implements OnInit, OnDestroy {
  @ViewChild(MatPaginator)
  private paginator: MatPaginator;

  private $leaveApplications: ISubscription;
  public leaveApplications: Array<LeaveApplication>;
  public isLoadingResults: boolean;
  public displayedColumns: Array<string> = ['applicationId', 'from', 'to', 'status', 'ics'];
  public resultsLength = 0;
  public dataSource: MatTableDataSource<LeaveApplication>;

  constructor(private _myApplications: MyApplicationsService,
              private _jwtHelper: JwtHelperService,
              private _notificationService: NotificationService,
              private _errorResolver: ErrorResolverService) {
  }

  ngOnInit() {
    this.isLoadingResults = true;
    this.fetchLeaveApplications();
  }

  ngOnDestroy(): void {
    if (this.$leaveApplications !== undefined) {
      this.$leaveApplications.unsubscribe();
    }
  }

  public fetchLeaveApplications(): void {
    const subjectId: number = this._jwtHelper.getSubjectId();
    this._myApplications
      .getSubmittedLeaveApplications(subjectId)
      .subscribe((res: Array<LeaveApplication>) => {
        this.leaveApplications = res;
        this.isLoadingResults = false;
        this.resultsLength = res.length;
        this.dataSource = new MatTableDataSource<LeaveApplication>(res);
        this.dataSource.paginator = this.paginator;
      }, (error: any) => {
        this._errorResolver.handleError(error);
      });
  }

}
