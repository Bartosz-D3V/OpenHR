import { MomentInput } from 'moment';

import { Application } from '@shared/domain/application/application';
import { LeaveType } from './leave-type';

export class LeaveApplication extends Application {
  public message?: string;
  public leaveType: LeaveType;

  constructor(applicationId: number, startDate: MomentInput,
              endDate: MomentInput, approvedByManager: boolean,
              approvedByHR: boolean, terminated: boolean,
              processInstanceId: string, leaveType: LeaveType,
              message?: string) {
    super(applicationId, startDate, endDate, approvedByManager, approvedByHR, terminated, processInstanceId);
    this.leaveType = leaveType;
    this.message = message;
  }
}
