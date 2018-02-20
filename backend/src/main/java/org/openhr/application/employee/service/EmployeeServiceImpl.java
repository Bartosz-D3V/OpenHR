package org.openhr.application.employee.service;

import org.openhr.application.authentication.service.AuthenticationService;
import org.openhr.application.employee.dao.EmployeeDAO;
import org.openhr.application.employee.repository.EmployeeRepository;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
  private final EmployeeDAO employeeDAO;
  private final EmployeeRepository employeeRepository;
  private final AuthenticationService authenticationService;

  public EmployeeServiceImpl(final EmployeeDAO employeeDAO,
                             final EmployeeRepository employeeRepository,
                             final AuthenticationService authenticationService) {
    this.employeeDAO = employeeDAO;
    this.employeeRepository = employeeRepository;
    this.authenticationService = authenticationService;
  }

  @Override
  public Employee getEmployee(final long subjectId) {
    return employeeDAO.getEmployee(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Employee createEmployee(final Employee employee) {
    final User user = employee.getUser();
    final String encodedPassword = authenticationService.encodePassword(user.getPassword());
    user.setPassword(encodedPassword);
    user.setUserRoles(authenticationService.setBasicUserRoles(user));
    return employeeDAO.createEmployee(employee);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Employee updateEmployee(final long subjectId, final Employee employee) {
    return employeeDAO.updateEmployee(subjectId, employee);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Manager setManagerToEmployee(final long subjectId, final Employee employee) {
    return employeeDAO.setManagerToEmployee(subjectId, employee);
  }
}
