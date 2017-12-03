import { MomentInput } from 'moment';

import { Subject } from '../../personal-details/domain/subject';

export class LeaveApplication {
  public subject: Subject;
  public startDate: MomentInput;
  public endDate: MomentInput;
  public message?: string;
  public leaveType: string;
  public approvedByManager = false;
  public approvedByHR = false;

  constructor() {
  }

}
