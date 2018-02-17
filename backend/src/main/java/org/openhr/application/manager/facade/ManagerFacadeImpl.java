package org.openhr.application.manager.facade;

import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.application.manager.service.ManagerService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
public class ManagerFacadeImpl implements ManagerFacade {

  private final ManagerService managerService;

  public ManagerFacadeImpl(final ManagerService managerService) {
    this.managerService = managerService;
  }

  @Override
  public Manager createManager(final Manager manager) {
    return managerService.createManager(manager);
  }

  @Override
  public void updateManager(final Manager manager) throws SubjectDoesNotExistException {
    managerService.updateManager(manager);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Manager> getManagers() {
    return managerService.getManagers();
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
