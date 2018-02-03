package org.openhr.application.manager.service;

import org.openhr.application.manager.dao.ManagerDAO;
import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ManagerServiceImpl implements ManagerService {
  private final ManagerDAO managerDAO;

  public ManagerServiceImpl(final ManagerDAO managerDAO) {
    this.managerDAO = managerDAO;
  }

  @Override
  public Manager addManager(final Manager manager) {
    return managerDAO.addManager(manager);
  }

  @Override
  public void updateManager(final Manager manager) {
    managerDAO.updateManager(manager);
  }

  @Override
  public Set<Employee> getEmployees(final long managerId) throws SubjectDoesNotExistException {
    return managerDAO.getEmployees(managerId);
  }

  @Override
  public void addEmployeeToManager(final Employee employee, final long managerId) throws SubjectDoesNotExistException {
    managerDAO.addEmployeeToManager(employee, managerId);
  }
}
