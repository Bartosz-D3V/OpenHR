package org.openhr.application.manager.dao;

import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;

import java.util.Set;

public interface ManagerDAO {

  Manager getManager(long subjectId);

  Manager addManager(Manager manager);

  void updateManager(Manager manager) throws SubjectDoesNotExistException;

  Set<Employee> getEmployees(long subjectId) throws SubjectDoesNotExistException;

  void addEmployeeToManager(Employee employee, long subjectId) throws SubjectDoesNotExistException;
}
