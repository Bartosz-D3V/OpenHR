import { Pipe, PipeTransform } from '@angular/core';

import { LeaveApplication } from '../../../../../shared/domain/leave-application/leave-application';
import { LeaveApplicationStatuses } from '../enumeration/leave-application-statuses.enum';

@Pipe({
  name: 'leaveApplicationStatus',
})
export class LeaveApplicationStatusPipe implements PipeTransform {

  transform(leaveApplication: LeaveApplication): LeaveApplicationStatuses {
    if (leaveApplication.terminated) {
      return !leaveApplication.approvedByManager ?
        LeaveApplicationStatuses.REJECTEDBYMANAGER :
        !leaveApplication.approvedByHR ?
          LeaveApplicationStatuses.REJECTEDBYHR :
          LeaveApplicationStatuses.ACCEPTED;
    }
    return LeaveApplicationStatuses.AWAITING;
  }

}
