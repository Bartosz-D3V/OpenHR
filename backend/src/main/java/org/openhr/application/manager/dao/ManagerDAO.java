package org.openhr.application.manager.dao;

import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;

import java.util.Set;

public interface ManagerDAO {

  Manager addManager(Manager manager);

  Manager getManager(long managerId) throws SubjectDoesNotExistException;

  void updateManager(Manager manager) throws SubjectDoesNotExistException;

  Set<Employee> getEmployees(long managerId) throws SubjectDoesNotExistException;

  void addEmployeeToManager(Employee employee, long managerId) throws SubjectDoesNotExistException;
}
