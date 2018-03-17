package org.openhr.application.delegationapplication.facade;

import org.openhr.application.delegationapplication.domain.DelegationApplication;
import org.openhr.common.domain.country.Country;
import org.openhr.common.exception.SubjectDoesNotExistException;

import java.util.List;

public interface DelegationApplicationFacade {
  List<Country> getCountries();

  DelegationApplication startDelegationApplicationProcess(long subjectId, DelegationApplication delegationApplication)
    throws SubjectDoesNotExistException;

  void rejectDelegationApplicationByManager(String processInstanceId);

  void approveDelegationApplicationByManager(String processInstanceId);

  void rejectDelegationApplicationByHr(String processInstanceId);

  void approveDelegationApplicationByHr(String processInstanceId);
}