package org.openhr.application.delegationapplication.dao;

import org.openhr.application.delegationapplication.domain.DelegationApplication;

public interface DelegationApplicationDAO {
  DelegationApplication createDelegationApplication(DelegationApplication delegationApplication);

  DelegationApplication getDelegationApplication(long delegationApplicationId);

  DelegationApplication updateDelegationApplication(DelegationApplication delegationApplication);
}