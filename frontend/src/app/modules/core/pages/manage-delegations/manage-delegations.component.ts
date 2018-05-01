import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';
import { ISubscription } from 'rxjs/Subscription';
import 'rxjs/add/observable/of';

import { JwtHelperService } from '@shared/services/jwt/jwt-helper.service';
import { ErrorResolverService } from '@shared/services/error-resolver/error-resolver.service';
import { NotificationService } from '@shared/services/notification/notification.service';
import { DelegationApplication } from '@shared/domain/application/delegation-application';
import { ManageDelegationsService } from '@modules/core/pages/manage-delegations/service/manage-delegations.service';
import { LightweightSubjectService } from '@modules/core/core-wrapper/service/lightweight-subject.service';
import { LightweightSubject } from '@shared/domain/subject/lightweight-subject';

@Component({
  selector: 'app-manage-delegations',
  templateUrl: './manage-delegations.component.html',
  styleUrls: ['./manage-delegations.component.scss'],
  providers: [LightweightSubjectService, ManageDelegationsService, JwtHelperService, NotificationService],
})
export class ManageDelegationsComponent implements OnInit, OnDestroy {
  private $delegationApplications: ISubscription;
  private $subjects: ISubscription;
  private subjects: Map<string, LightweightSubject> = new Map<string, LightweightSubject>();
  public isLoadingResults: boolean;
  public displayedColumns: Array<string> = [
    'applicationId',
    'from',
    'to',
    'employeeName',
    'country',
    'city',
    'budget',
    'reject',
    'approve',
  ];
  public dataSource: MatTableDataSource<DelegationApplication>;

  @ViewChild(MatPaginator) public paginator: MatPaginator;

  constructor(
    private _lightweightSubjectService: LightweightSubjectService,
    private _manageDelegationsService: ManageDelegationsService,
    private _jwtHelper: JwtHelperService,
    private _notificationService: NotificationService,
    private _errorResolver: ErrorResolverService
  ) {}

  ngOnInit(): void {
    this.isLoadingResults = true;
    this.fetchDelegationApplications();
  }

  ngOnDestroy(): void {
    this.unsubscribeAll();
  }

  private unsubscribeAll(): void {
    if (this.$delegationApplications) {
      this.$delegationApplications.unsubscribe();
    }
    if (this.$subjects) {
      this.$subjects.unsubscribe();
    }
  }

  public fetchDelegationApplications(): void {
    this.isLoadingResults = true;
    this.$delegationApplications = this._manageDelegationsService
      .getAwaitingForActionDelegationApplications(this._jwtHelper.getSubjectId())
      .subscribe(
        (result: Array<DelegationApplication>) => {
          this.dataSource = new MatTableDataSource<DelegationApplication>(result);
          this.dataSource.paginator = this.paginator;
          this.isLoadingResults = false;
        },
        (httpErrorResponse: HttpErrorResponse) => {
          this._errorResolver.handleError(httpErrorResponse.error);
        }
      );
  }

  public approveDelegationApplication(processInstanceId: string): void {
    this._manageDelegationsService.approveDelegationApplicationByManager(processInstanceId).subscribe(
      () => {
        this.fetchDelegationApplications();
        const message = 'Application has been accepted';
        this._notificationService.openSnackBar(message, 'OK');
      },
      (httpErrorResponse: HttpErrorResponse) => {
        this._errorResolver.handleError(httpErrorResponse.error);
      }
    );
  }

  public rejectDelegationApplication(processInstanceId: string): void {
    this._manageDelegationsService.rejectDelegationApplicationByManager(processInstanceId).subscribe(
      () => {
        this.fetchDelegationApplications();
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
