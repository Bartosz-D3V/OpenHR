package org.openhr.facade.manager;

import org.openhr.domain.subject.Employee;
import org.openhr.domain.subject.Manager;
import org.openhr.exception.SubjectDoesNotExistException;
import org.openhr.service.manager.ManagerService;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ManagerFacadeImpl implements ManagerFacade {

  private final ManagerService managerService;

  public ManagerFacadeImpl(final ManagerService managerService) {
    this.managerService = managerService;
  }

  @Override
  public Manager addManager(final Manager manager) {
    return managerService.addManager(manager);
  }

  @Override
  public Set<Employee> getEmployees(final long managerId) throws SubjectDoesNotExistException {
    return managerService.getEmployees(managerId);
  }
}
