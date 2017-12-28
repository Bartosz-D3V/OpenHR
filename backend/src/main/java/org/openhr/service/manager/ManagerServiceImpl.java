package org.openhr.service.manager;

import org.openhr.dao.manager.ManagerDAO;
import org.openhr.domain.subject.Employee;
import org.openhr.domain.subject.Manager;
import org.openhr.exception.SubjectDoesNotExistException;
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
  public Set<Employee> getEmployees(final long managerId) throws SubjectDoesNotExistException {
    return managerDAO.getEmployees(managerId);
  }
}
