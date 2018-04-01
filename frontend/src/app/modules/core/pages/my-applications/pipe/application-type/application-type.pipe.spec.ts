import { DelegationApplication } from '@shared/domain/application/delegation-application';
import { LeaveApplication } from '@shared/domain/application/leave-application';
import { LeaveType } from '@shared/domain/application/leave-type';
import { ApplicationTypePipe } from './application-type.pipe';

describe('ApplicationTypePipe', () => {
  const mockLeaveApplication: LeaveApplication =
    new LeaveApplication(null, null, null, null, null, null, null, null);
  const mockDelegationApplication: DelegationApplication =
    new DelegationApplication(null, null, null, null, null, null, null, null, null, null, null);
  let pipe: ApplicationTypePipe;

  beforeAll(() => {
    pipe = new ApplicationTypePipe();
  });

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('returns "Leave application" if passed object is of type LeaveApplication', () => {
    mockLeaveApplication.leaveType = new LeaveType(null, null, null);

    expect(pipe.transform(mockLeaveApplication)).toEqual('Leave application');
  });
  it('returns "Delegation application" if passed object is of type DelegationApplication', () => {
    mockDelegationApplication.budget = 100;

    expect(pipe.transform(mockDelegationApplication)).toEqual('Delegation application');
  });

});
