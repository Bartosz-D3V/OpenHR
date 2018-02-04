package org.openhr.application.manager.facade;

import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.application.manager.service.ManagerService;
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
  public void updateManager(final Manager manager) {
    managerService.updateManager(manager);
  }

  @Override
  public Set<Employee> getEmployees(final long managerId) throws SubjectDoesNotExistException {
    return managerService.getEmployees(managerId);
  }

  @Override
  public void addEmployeeToManager(final Employee employee, final long managerId) throws SubjectDoesNotExistException {
    managerService.addEmployeeToManager(employee, managerId);
  }
}