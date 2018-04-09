package org.openhr.application.delegation.facade;

import org.openhr.application.delegation.domain.DelegationApplication;
import org.openhr.common.domain.country.Country;
import org.openhr.common.exception.SubjectDoesNotExistException;

import java.util.List;

public interface DelegationApplicationFacade {
  List<Country> getCountries();

  DelegationApplication startDelegationApplicationProcess(long subjectId, DelegationApplication delegationApplication)
    throws SubjectDoesNotExistException;

  DelegationApplication getDelegationApplication(long delegationApplicationId);

  void updateDelegationApplication(String processInstanceId, DelegationApplication delegationApplication);

  List<DelegationApplication> getSubjectsDelegationApplications(long subjectId);

  List<DelegationApplication> getAwaitingForActionDelegationApplications(long subjectId);

  void rejectDelegationApplicationByManager(String processInstanceId);

  void approveDelegationApplicationByManager(String processInstanceId);

  void rejectDelegationApplicationByHr(String processInstanceId);

  void approveDelegationApplicationByHr(String processInstanceId);
}
