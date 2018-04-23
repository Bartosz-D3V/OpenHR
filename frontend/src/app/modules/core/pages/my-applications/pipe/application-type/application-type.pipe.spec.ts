import { DelegationApplication } from '@shared/domain/application/delegation-application';
import { LeaveApplication } from '@shared/domain/application/leave-application';
import { ApplicationTypePipe } from './application-type.pipe';

describe('ApplicationTypePipe', () => {
  const mockLeaveApplication: LeaveApplication = new LeaveApplication(null, null, null, null, null, null, null, null);
  const mockDelegationApplication: DelegationApplication = new DelegationApplication(
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null
  );
  let pipe: ApplicationTypePipe;

  beforeAll(() => {
    pipe = new ApplicationTypePipe();
  });

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('returns "Leave application" if passed object is of type LeaveApplication', () => {
    expect(pipe.transform(mockLeaveApplication)).toEqual('Leave application');
  });

  it('returns "Delegation application" if passed object is of type DelegationApplication', () => {
    expect(pipe.transform(mockDelegationApplication)).toEqual('Delegation application');
  });

  it('returns null if passed object is not recognised', () => {
    mockDelegationApplication.applicationType = null;
    expect(pipe.transform(mockDelegationApplication)).toBeNull();
  });
});
