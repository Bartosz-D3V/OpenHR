import { MomentInput } from 'moment';

export class LeaveApplication {
  public subjectId: number;
  public startDate: MomentInput;
  public endDate: MomentInput;
  public message?: string;
  public leaveType: string;
  public approvedByManager = false;
  public approvedByHR = false;

  constructor() {
  }

}
