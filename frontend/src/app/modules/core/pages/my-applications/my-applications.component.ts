import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { ISubscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';

import { NotificationService } from '@shared/services/notification/notification.service';
import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { Application } from '@shared/domain/application/application';
import { MyApplicationsService } from './service/my-applications.service';
import { el } from '@angular/platform-browser/testing/src/browser_util';

@Component({
  selector: 'app-my-applications',
  templateUrl: './my-applications.component.html',
  styleUrls: ['./my-applications.component.scss'],
  providers: [MyApplicationsService, JwtHelperService, NotificationService],
})
export class MyApplicationsComponent implements OnInit, OnDestroy {
  private $leaveApplications: ISubscription;
  public isLoadingResults: boolean;
  public displayedColumns: Array<string> = ['type', 'from', 'to', 'status', 'ics', 'info', 'action'];
  public resultsLength = 0;
  public dataSource: MatTableDataSource<Application> = new MatTableDataSource<Application>();

  @ViewChild(MatPaginator) public paginator: MatPaginator;

  constructor(
    private _myApplications: MyApplicationsService,
    private _jwtHelper: JwtHelperService,
    private _errorResolver: ErrorResolverService,
    private _router: Router,
    private _activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.isLoadingResults = true;
    this.fetchApplications();
  }

  ngOnDestroy(): void {
    if (this.$leaveApplications !== undefined) {
      this.$leaveApplications.unsubscribe();
    }
  }

  public fetchApplications(): void {
    const subjectId: number = this._jwtHelper.getSubjectId();
    Observable.zip(
      this._myApplications.getSubmittedLeaveApplications(subjectId),
      this._myApplications.getSubmittedDelegationApplications(subjectId),
      (leaveApplications: Array<Application>, delegationApplications: Array<Application>) => ({ leaveApplications, delegationApplications })
    ).subscribe(
      pair => {
        this.isLoadingResults = false;
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

  public applicationIsRejected(application: Application): boolean {
    return application.terminated && !(application.approvedByManager || application.approvedByHR);
  }

  public amendApplication(applicationId: number): void {
    this._router.navigate(['../delegation', applicationId], { relativeTo: this._activatedRoute });
  }
}
