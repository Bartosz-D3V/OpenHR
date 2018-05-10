package org.openhr.application.manager.facade;

import java.util.List;
import java.util.Set;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.manager.service.ManagerService;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserAlreadyExists;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ManagerFacadeImpl implements ManagerFacade {

  private final ManagerService managerService;

  public ManagerFacadeImpl(final ManagerService managerService) {
    this.managerService = managerService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Manager getManager(final long subjectId) throws SubjectDoesNotExistException {
    return managerService.getManager(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Manager> getManagers() {
    return managerService.getManagers();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Manager addManager(final Manager manager) throws UserAlreadyExists {
    return managerService.addManager(manager);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Manager updateManager(final long subjectId, final Manager manager)
      throws SubjectDoesNotExistException {
    return managerService.updateManager(subjectId, manager);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void deleteManager(final long subjectId) throws SubjectDoesNotExistException {
    managerService.deleteManager(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Set<Employee> getManagersEmployees(final long subjectId)
      throws SubjectDoesNotExistException {
    return managerService.getManagersEmployees(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void addEmployeeToManager(final long managerId, final long subjectId)
      throws SubjectDoesNotExistException {
    managerService.addEmployeeToManager(managerId, subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Manager setHrToManager(final long managerId, final long hrTeamMemberId)
      throws SubjectDoesNotExistException {
    return managerService.setHrToManager(managerId, hrTeamMemberId);
  }
}
