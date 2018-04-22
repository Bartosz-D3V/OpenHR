package org.openhr.application.delegation.service;

import java.util.List;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.openhr.application.delegation.domain.DelegationApplication;
import org.openhr.common.domain.country.Country;
import org.openhr.common.domain.subject.Subject;

public interface DelegationApplicationService {
  List<Country> getCountries();

  DelegationApplication createDelegationApplication(
      Subject subject, DelegationApplication delegationApplication);

  DelegationApplication getDelegationApplication(long delegationApplicationId);

  DelegationApplication updateDelegationApplication(DelegationApplication delegationApplication);

  DelegationApplication saveProcessInstanceId(
      DelegationApplication delegationApplication, ExecutionEntityImpl delegateExecution);

  List<DelegationApplication> getSubjectsDelegationApplications(long subjectId);

  List<DelegationApplication> getAwaitingForActionDelegationApplications(long subjectId);

  void assignToApplicant(DelegationApplication delegationApplication);

  void assignToManager(DelegationApplication delegationApplication);

  void assignToHr(DelegationApplication delegationApplication);

  void approveByManager(DelegationApplication delegationApplication);

  void rejectByManager(DelegationApplication delegationApplication);

  void approveByHr(DelegationApplication delegationApplication);

  void rejectByHr(DelegationApplication delegationApplication);

  void terminateDelegationApplication(DelegationApplication delegationApplication);

  void resumeDelegationApplication(DelegationApplication delegationApplication);
}
