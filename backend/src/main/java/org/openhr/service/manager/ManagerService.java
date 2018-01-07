package org.openhr.service.manager;

import org.openhr.domain.subject.Employee;
import org.openhr.domain.subject.Manager;
import org.openhr.exception.SubjectDoesNotExistException;

import java.util.Set;

public interface ManagerService {

  Manager addManager(Manager manager);

  void updateManager(Manager manager);

  Set<Employee> getEmployees(long managerId) throws SubjectDoesNotExistException;

  void addEmployeeToManager(Employee employee, long managerId) throws SubjectDoesNotExistException;
}
