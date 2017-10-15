import { DelegationApplication } from './delegation-application';

describe('delegation-application', () => {
  let delegationApplication: DelegationApplication;

  beforeEach(() => {
    delegationApplication = new DelegationApplication();
  });

  describe('initial property', () => {
    it('canExceedExpenses should be false', () => {
      expect(delegationApplication.canExceedExpenses).toBeFalsy();
    });

    it('approvedByManager should be false', () => {
      expect(delegationApplication.approvedByManager).toBeFalsy();
    });

    it('approvedByHR should be false', () => {
      expect(delegationApplication.approvedByHR).toBeFalsy();
    });
  });
});
