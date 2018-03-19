package org.openhr.application.delegationapplication.command;

import org.openhr.application.delegationapplication.domain.DelegationApplication;
import org.openhr.common.domain.subject.Subject;

public interface DelegationApplicationCommand {
  String startDelegationProcess(Subject subject, DelegationApplication delegationApplication);

  void approveByManager(String processInstanceId);

  void rejectByManager(String processInstanceId);

  void approveByHr(String processInstanceId);

  void rejectByHr(String processInstanceId);
}
