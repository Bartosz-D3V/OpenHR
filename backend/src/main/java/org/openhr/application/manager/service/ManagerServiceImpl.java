package org.openhr.application.manager.service;

import org.openhr.application.authentication.service.AuthenticationService;
import org.openhr.application.employee.service.EmployeeService;
import org.openhr.application.manager.dao.ManagerDAO;
import org.openhr.application.manager.repository.ManagerRepository;
import org.openhr.application.user.domain.User;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.manager.domain.Manager;
import org.openhr.common.enumeration.Role;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ManagerServiceImpl implements ManagerService {
  private final ManagerDAO managerDAO;
  private final ManagerRepository managerRepository;
  private final AuthenticationService authenticationService;
  private final EmployeeService employeeService;

  public ManagerServiceImpl(final ManagerDAO managerDAO,
                            final ManagerRepository managerRepository,
                            final AuthenticationService authenticationService,
                            final EmployeeService employeeService) {
    this.managerDAO = managerDAO;
    this.managerRepository = managerRepository;
    this.authenticationService = authenticationService;
    this.employeeService = employeeService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Manager getManager(final long subjectId) {
    return managerDAO.getManager(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Manager addManager(final Manager manager) {
    final User user = manager.getUser();
    final String encodedPassword = authenticationService.encodePassword(user.getPassword());
    user.setPassword(encodedPassword);
    user.setUserRoles(authenticationService.setManagerUserRole(user));
    manager.setRole(Role.MANAGER);
    return managerDAO.addManager(manager);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Manager updateManager(final Manager manager) throws SubjectDoesNotExistException {
    return managerDAO.updateManager(manager);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Manager> getManagers() {
    return managerRepository.getManagers();
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Set<Employee> getEmployees(final long subjectId) throws SubjectDoesNotExistException {
    return managerDAO.getEmployees(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void addEmployeeToManager(final long managerId, final long subjectId) throws SubjectDoesNotExistException {
    final Manager manager = getManager(managerId);
    final Employee employee = employeeService.getEmployee(subjectId);
    employee.setManager(manager);
    managerDAO.addEmployeeToManager(manager, employee);
  }
}
