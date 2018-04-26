package org.openhr.application.employee.service;

import java.util.List;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.employee.repository.EmployeeRepository;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.service.UserService;
import org.openhr.common.enumeration.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
  private final EmployeeRepository employeeRepository;
  private final UserService userService;

  public EmployeeServiceImpl(
      final EmployeeRepository employeeRepository, final UserService userService) {
    this.employeeRepository = employeeRepository;
    this.userService = userService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Employee> getEmployees() {
    return employeeRepository.getEmployees();
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Employee getEmployee(final long subjectId) {
    return employeeRepository.getEmployee(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public Employee createEmployee(final Employee employee) {
    final User user = employee.getUser();
    final String encodedPassword = userService.encodePassword(user.getPassword());
    user.setPassword(encodedPassword);
    user.setUserRoles(userService.setBasicUserRoles(user));
    employee.setRole(Role.EMPLOYEE);
    employee.setUser(user);

    return employeeRepository.createEmployee(employee);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public Employee updateEmployee(final long subjectId, final Employee employee) {
    return employeeRepository.updateEmployee(subjectId, employee);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public Manager setManagerToEmployee(final long employeeId, final Manager manager) {
    return employeeRepository.setManagerToEmployee(employeeId, manager);
  }
}
