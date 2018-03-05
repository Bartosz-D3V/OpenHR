import { LeaveApplicationStatusPipe } from './leave-application-status.pipe';
import { LeaveApplication } from '../../../../../shared/domain/leave-application/leave-application';
import { LeaveApplicationStatuses } from '../enumeration/leave-application-statuses.enum';

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
      mockLeaveApplication = new LeaveApplication();
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
