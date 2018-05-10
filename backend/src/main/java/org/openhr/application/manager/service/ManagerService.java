package org.openhr.application.manager.service;

import java.util.List;
import java.util.Set;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.manager.domain.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserAlreadyExists;

public interface ManagerService {

  Manager getManager(long subjectId) throws SubjectDoesNotExistException;

  List<Manager> getManagers();

  Manager addManager(Manager manager) throws UserAlreadyExists;

  Manager updateManager(long subjectId, Manager manager) throws SubjectDoesNotExistException;

  void deleteManager(long subjectId) throws SubjectDoesNotExistException;

  Set<Employee> getManagersEmployees(long subjectId) throws SubjectDoesNotExistException;

  void addEmployeeToManager(long managerId, long subjectId) throws SubjectDoesNotExistException;

  Manager setHrToManager(long managerId, long hrTeamMemberId) throws SubjectDoesNotExistException;
}
