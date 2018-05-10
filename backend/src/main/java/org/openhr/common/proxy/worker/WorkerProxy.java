package org.openhr.common.proxy.worker;

import org.openhr.application.employee.domain.Employee;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.manager.domain.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;

public interface WorkerProxy {
  Employee getEmployee(long employeeId);

  Manager getManager(long mangerId) throws SubjectDoesNotExistException;

  HrTeamMember getHrTeamMember(long hrTeamMemberId);
}
