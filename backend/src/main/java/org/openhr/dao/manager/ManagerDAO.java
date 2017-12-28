package org.openhr.dao.manager;

import org.openhr.domain.subject.Employee;
import org.openhr.domain.subject.Manager;
import org.openhr.exception.SubjectDoesNotExistException;

import java.util.Set;

public interface ManagerDAO {

  Manager addManager(Manager manager);

  Manager getManager(long managerId) throws SubjectDoesNotExistException;

  Set<Employee> getEmployees(long managerId) throws SubjectDoesNotExistException;
}
