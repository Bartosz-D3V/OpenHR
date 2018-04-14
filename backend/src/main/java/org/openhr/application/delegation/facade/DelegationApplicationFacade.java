package org.openhr.application.delegation.facade;

import java.util.List;
import org.openhr.application.delegation.domain.DelegationApplication;
import org.openhr.common.domain.country.Country;
import org.openhr.common.exception.SubjectDoesNotExistException;

public interface DelegationApplicationFacade {
  List<Country> getCountries();

  DelegationApplication startDelegationApplicationProcess(
      long subjectId, DelegationApplication delegationApplication)
      throws SubjectDoesNotExistException;

  DelegationApplication getDelegationApplication(long delegationApplicationId);

  DelegationApplication updateDelegationApplication(
      String processInstanceId, DelegationApplication delegationApplication);

  List<DelegationApplication> getSubjectsDelegationApplications(long subjectId);

  List<DelegationApplication> getAwaitingForActionDelegationApplications(long subjectId);

  void rejectDelegationApplicationByManager(String processInstanceId);

  void approveDelegationApplicationByManager(String processInstanceId);

  void rejectDelegationApplicationByHr(String processInstanceId);

  void approveDelegationApplicationByHr(String processInstanceId);
}
