import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { ISubscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/retry';

import { SystemVariables } from '@config/system-variables';
import { NotificationService } from '@shared/services/notification/notification.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { Application } from '@shared/domain/application/application';
import { MyApplicationsService } from './service/my-applications.service';

@Component({
  selector: 'app-my-applications',
  templateUrl: './my-applications.component.html',
  styleUrls: ['./my-applications.component.scss'],
  providers: [MyApplicationsService, JwtHelperService, NotificationService],
})
export class MyApplicationsComponent implements OnInit, OnDestroy {
  private $leaveApplications: ISubscription;
  public isFetching: boolean;
  public displayedColumns: Array<string> = ['type', 'from', 'to', 'status', 'ics', 'info', 'action'];
  public resultsLength = 0;
  public dataSource: MatTableDataSource<Application> = new MatTableDataSource<Application>();

  @ViewChild(MatPaginator)
  public paginator: MatPaginator;

  constructor(
    private _myApplications: MyApplicationsService,
    private _jwtHelper: JwtHelperService,
    private _errorResolver: ErrorResolverService,
    private _router: Router,
    private _activatedRoute: ActivatedRoute
  ) {}

  public ngOnInit(): void {
    this.fetchApplications();
  }

  public ngOnDestroy(): void {
    if (this.$leaveApplications) {
      this.$leaveApplications.unsubscribe();
    }
  }

  public fetchApplications(): void {
    this.isFetching = true;
    const subjectId: number = this._jwtHelper.getSubjectId();
    Observable.zip(
      this._myApplications.getSubmittedLeaveApplications(subjectId),
      this._myApplications.getSubmittedDelegationApplications(subjectId),
      (leaveApplications: Array<Application>, delegationApplications: Array<Application>) => ({ leaveApplications, delegationApplications })
    )
      .retry(SystemVariables.RETRY_TIMES)
      .finally(() => (this.isFetching = false))
      .subscribe(
        pair => {
          const applications: Array<Application> = pair.leaveApplications.concat(pair.delegationApplications);
          this.resultsLength = applications.length;
          this.dataSource.data = applications;
          this.dataSource.paginator = this.paginator;
        },
        (httpErrorResponse: HttpErrorResponse) => {
          this._errorResolver.handleError(httpErrorResponse.error);
        }
      );
  }

  public downloadICS(applicationId: number): void {
    this.$leaveApplications = this._myApplications
      .downloadICS(applicationId)
      .retry(SystemVariables.RETRY_TIMES)
      .subscribe(
        (data: HttpResponse<any>) => {
          const blob: Blob = new Blob([data], { type: 'text/calendar' });
          const url: string = window.URL.createObjectURL(blob);
          window.open(url, '_blank');
        },
        (httpErrorResponse: HttpErrorResponse) => {
          this._errorResolver.handleError(httpErrorResponse.error);
        }
      );
  }

  public applicationIsRejected(application: Application): boolean {
    return application.terminated && !(application.approvedByManager || application.approvedByHR);
  }

  public amendApplication(applicationId: number): void {
    this._router.navigate(['../delegation', applicationId], { relativeTo: this._activatedRoute });
  }
}
