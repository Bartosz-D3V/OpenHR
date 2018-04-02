import { MomentInput } from 'moment';

import { LeaveApplication } from 'app/shared/domain/application/leave-application';
import { ApplicationStatusPipe } from '@modules/core/pages/my-applications/pipe/leave-application-status/application-status.pipe';
import { ApplicationStatuses } from '@modules/core/pages/my-applications/enumeration/application-statuses.enum';

describe('ApplicationStatusPipe', () => {
  it('create an instance', () => {
    const pipe: ApplicationStatusPipe = new ApplicationStatusPipe();
    expect(pipe).toBeTruthy();
  });

  describe('transform method', () => {
    let pipe: ApplicationStatusPipe;
    let mockLeaveApplication: LeaveApplication;

    beforeEach(() => {
      pipe = new ApplicationStatusPipe();
      const mockStartDate: MomentInput = '2020-05-05';
      const mockEndDate: MomentInput = '2020-05-10';
      mockLeaveApplication = new LeaveApplication(null, mockStartDate, mockEndDate, false, false, false, null, null);
    });

    it('should return AWAITING status if application was not terminated', () => {
      expect(pipe.transform(mockLeaveApplication)).toEqual(ApplicationStatuses.AWAITING);
    });

    it('should return REJECTEDBYHR status if application was terminated and rejected by HR', () => {
      mockLeaveApplication.approvedByHR = false;
      mockLeaveApplication.approvedByManager = true;
      mockLeaveApplication.terminated = true;

      expect(pipe.transform(mockLeaveApplication)).toEqual(ApplicationStatuses.REJECTEDBYHR);
    });

    it('should return REJECTEDBYMANAGER status if application was terminated and rejected by Manager', () => {
      mockLeaveApplication.approvedByManager = false;
      mockLeaveApplication.terminated = true;

      expect(pipe.transform(mockLeaveApplication)).toEqual(ApplicationStatuses.REJECTEDBYMANAGER);
    });

    it('should return ACCEPTED status if application was approved and terminated', () => {
      mockLeaveApplication.approvedByManager = true;
      mockLeaveApplication.approvedByHR = true;
      mockLeaveApplication.terminated = true;

      expect(pipe.transform(mockLeaveApplication)).toEqual(ApplicationStatuses.ACCEPTED);
    });
  });
});
