package org.openhr.application.manager.service;

import org.openhr.application.employee.domain.Employee;
import org.openhr.application.manager.domain.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;

import java.util.List;
import java.util.Set;

public interface ManagerService {

  Manager getManager(long subjectId);

  Manager addManager(Manager manager);

  Manager updateManager(Manager manager) throws SubjectDoesNotExistException;

  List<Manager> getManagers();

  Set<Employee> getEmployees(long subjectId) throws SubjectDoesNotExistException;

  void addEmployeeToManager(long managerId, long subjectId) throws SubjectDoesNotExistException;
}
