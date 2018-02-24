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
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Manager getManager(final long subjectId) {
    return managerService.getManager(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Manager addManager(final Manager manager) {
    return managerService.addManager(manager);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Manager updateManager(final Manager manager) throws SubjectDoesNotExistException {
    return managerService.updateManager(manager);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Manager> getManagers() {
    return managerService.getManagers();
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Set<Employee> getEmployees(final long subjectId) throws SubjectDoesNotExistException {
    return managerService.getEmployees(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void addEmployeeToManager(final long managerId, final long subjectId) throws SubjectDoesNotExistException {
    managerService.addEmployeeToManager(managerId, subjectId);
  }
}
