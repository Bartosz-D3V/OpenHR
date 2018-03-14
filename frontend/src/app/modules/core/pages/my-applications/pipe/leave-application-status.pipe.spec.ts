import { MomentInput } from 'moment';

import { LeaveApplication } from '@shared/domain/application/leave-application';
import { LeaveApplicationStatuses } from '../enumeration/leave-application-statuses.enum';
import { LeaveApplicationStatusPipe } from './leave-application-status.pipe';

describe('LeaveApplicationStatusPipe', () => {
  it('create an instance', () => {
    const pipe: LeaveApplicationStatusPipe = new LeaveApplicationStatusPipe();
    expect(pipe).toBeTruthy();
  });

  describe('transform method', () => {
    let pipe: LeaveApplicationStatusPipe;
    let mockLeaveApplication: LeaveApplication;

    beforeEach(() => {
      pipe = new LeaveApplicationStatusPipe();
      const mockStartDate: MomentInput = '2020-05-05';
      const mockEndDate: MomentInput = '2020-05-10';
      mockLeaveApplication = new LeaveApplication(null, mockStartDate, mockEndDate, false, false, false, null, null);
    });

    it('should return AWAITING status if application was not terminated', () => {
      expect(pipe.transform(mockLeaveApplication)).toEqual(LeaveApplicationStatuses.AWAITING);
    });

    it('should return REJECTEDBYHR status if application was terminated and rejected by HR', () => {
      mockLeaveApplication.approvedByHR = false;
      mockLeaveApplication.approvedByManager = true;
      mockLeaveApplication.terminated = true;

      expect(pipe.transform(mockLeaveApplication)).toEqual(LeaveApplicationStatuses.REJECTEDBYHR);
    });

    it('should return REJECTEDBYMANAGER status if application was terminated and rejected by Manager', () => {
      mockLeaveApplication.approvedByManager = false;
      mockLeaveApplication.terminated = true;

      expect(pipe.transform(mockLeaveApplication)).toEqual(LeaveApplicationStatuses.REJECTEDBYMANAGER);
    });

    it('should return ACCEPTED status if application was approved and terminated', () => {
      mockLeaveApplication.approvedByManager = true;
      mockLeaveApplication.approvedByHR = true;
      mockLeaveApplication.terminated = true;

      expect(pipe.transform(mockLeaveApplication)).toEqual(LeaveApplicationStatuses.ACCEPTED);
    });
  });
});
