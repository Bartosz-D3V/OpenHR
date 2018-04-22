package org.openhr.application.manager.dao;

import java.util.Set;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.manager.domain.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;

public interface ManagerDAO {

  Manager getManager(long subjectId);

  Manager addManager(Manager manager);

  Manager updateManager(Manager manager) throws SubjectDoesNotExistException;

  Set<Employee> getEmployees(long subjectId) throws SubjectDoesNotExistException;

  void addEmployeeToManager(Manager manager, Employee employee) throws SubjectDoesNotExistException;

  Manager setHrToManager(Manager manager, HrTeamMember hrTeamMember)
      throws SubjectDoesNotExistException;
}
