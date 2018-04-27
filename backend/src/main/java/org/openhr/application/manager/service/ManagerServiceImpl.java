package org.openhr.application.manager.service;

import java.util.List;
import java.util.Set;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.manager.repository.ManagerRepository;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.service.UserService;
import org.openhr.common.enumeration.Role;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.proxy.worker.WorkerProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManagerServiceImpl implements ManagerService {

  private final ManagerRepository managerRepository;
  private final UserService userService;
  private final WorkerProxy workerProxy;

  public ManagerServiceImpl(
      final ManagerRepository managerRepository,
      final UserService userService,
      final WorkerProxy workerProxy) {
    this.managerRepository = managerRepository;
    this.userService = userService;
    this.workerProxy = workerProxy;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Manager getManager(final long subjectId) {
    return managerRepository.getManager(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public Manager addManager(final Manager manager) {
    final User user = manager.getUser();
    final String encodedPassword = userService.encodePassword(user.getPassword());
    user.setPassword(encodedPassword);
    user.setUserRoles(userService.setManagerUserRole(user));
    manager.setRole(Role.MANAGER);
    return managerRepository.addManager(manager);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public Manager updateManager(final Manager manager) throws SubjectDoesNotExistException {
    return managerRepository.updateManager(manager);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Manager> getManagers() {
    return managerRepository.getManagers();
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Set<Employee> getEmployees(final long subjectId) throws SubjectDoesNotExistException {
    return managerRepository.getEmployees(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void addEmployeeToManager(final long managerId, final long subjectId)
      throws SubjectDoesNotExistException {
    final Manager manager = getManager(managerId);
    final Employee employee = workerProxy.getEmployee(subjectId);
    employee.setManager(manager);
    managerRepository.addEmployeeToManager(manager, employee);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public Manager setHrToManager(final long managerId, final long hrTeamMemberId)
      throws SubjectDoesNotExistException {
    final Manager manager = getManager(managerId);
    final HrTeamMember hrTeamMember = workerProxy.getHrTeamMember(hrTeamMemberId);
    manager.setHrTeamMember(hrTeamMember);
    return managerRepository.setHrToManager(manager, hrTeamMember);
  }
}
