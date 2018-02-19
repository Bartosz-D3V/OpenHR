import { MomentInput } from 'moment';

import { LeaveType } from './leave-type';

export class LeaveApplication {
  public applicationId: number;
  public subjectId: number;
  public startDate: MomentInput;
  public endDate: MomentInput;
  public message?: string;
  public leaveType: LeaveType;
  public approvedByManager = false;
  public approvedByHR = false;
  public processInstanceId: string;

  constructor() {
  }

}