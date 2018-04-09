package org.openhr.application.delegation.dao;

import org.openhr.application.delegation.domain.DelegationApplication;

public interface DelegationApplicationDAO {
  DelegationApplication createDelegationApplication(DelegationApplication delegationApplication);

  DelegationApplication getDelegationApplication(long delegationApplicationId);

  DelegationApplication updateDelegationApplication(DelegationApplication delegationApplication);
}
