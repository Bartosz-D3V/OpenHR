package org.openhr.application.manager.facade;

import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;

import java.util.List;
import java.util.Set;

public interface ManagerFacade {
  Manager getManager(long subjectId);

  Manager addManager(Manager manager);

  void updateManager(Manager manager) throws SubjectDoesNotExistException;

  List<Manager> getManagers();

  Set<Employee> getEmployees(long subjectId) throws SubjectDoesNotExistException;

  void addEmployeeToManager(Employee employee, long subjectId) throws SubjectDoesNotExistException;
}
