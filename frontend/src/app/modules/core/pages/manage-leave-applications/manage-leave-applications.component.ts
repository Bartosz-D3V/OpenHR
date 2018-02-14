import { Component, OnDestroy, OnInit } from '@angular/core';
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

  public leaveApplications: Array<LeaveApplication>;
  private $leaveApplications: ISubscription;

  constructor(private _manageLeaveApplicationsService: ManageLeaveApplicationsService,
              private _jwtHelper: JwtHelperService) {
  }

  ngOnInit() {
    this.fetchLeaveApplications();
  }

  ngOnDestroy(): void {
    this.$leaveApplications.unsubscribe();
  }


  private fetchLeaveApplications(): void {
    this.$leaveApplications = this._manageLeaveApplicationsService
      .getUnacceptedLeaveApplications(this._jwtHelper.getSubjectId())
      .subscribe((result: Array<LeaveApplication>) => this.leaveApplications = result,
        (error: any) => {
        });
  }
}
