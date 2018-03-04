import { MomentInput } from 'moment';

import { LeaveType } from './leave-type';

export class LeaveApplication {
  public applicationId: number;
  public startDate: MomentInput;
  public endDate: MomentInput;
  public message?: string;
  public leaveType: LeaveType;
  public approvedByManager: boolean;
  public approvedByHR: boolean;
  public terminated: boolean;
  public processInstanceId: string;

  constructor() {
  }

}
