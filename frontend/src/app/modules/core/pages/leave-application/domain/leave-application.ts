import { MomentInput } from 'moment';
export class LeaveApplication {
  public readonly subjectId: number;
  public leaveType: string;
  public startDate: MomentInput;
  public endDate: MomentInput;
  public message?: string;
  public readonly canExceedExpenses = false;
  public readonly approvedByManager = false;
  public readonly approvedByHR = false;

  constructor() {
  }
}
