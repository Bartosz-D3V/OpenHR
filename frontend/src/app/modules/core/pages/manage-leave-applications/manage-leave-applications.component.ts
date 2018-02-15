import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { ISubscription } from 'rxjs/Subscription';

import { LeaveApplication } from '../../../../shared/domain/leave-application/leave-application';
import { JwtHelperService } from '../../../../shared/services/jwt/jwt-helper.service';
import { ManageLeaveApplicationsService } from './service/manage-leave-applications.service';

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
              private _jwtHelper: JwtHelperService) {
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
      .getUnacceptedLeaveApplications(this._jwtHelper.getSubjectId())
      .subscribe((result: Array<LeaveApplication>) => {
          this.leaveApplications = result;
          this.dataSource = new MatTableDataSource<LeaveApplication>(result);
          this.dataSource.paginator = this.paginator;
          this.isLoadingResults = false;
          this.resultsLength = result.length;
        },
        (error: any) => {
        });
  }
}
