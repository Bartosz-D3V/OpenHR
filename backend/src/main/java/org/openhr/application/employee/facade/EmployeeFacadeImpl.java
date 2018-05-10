package org.openhr.application.employee.facade;

import java.util.List;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.employee.service.EmployeeService;
import org.openhr.application.manager.domain.Manager;
import org.openhr.common.exception.UserAlreadyExists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeFacadeImpl implements EmployeeFacade {
  private final EmployeeService employeeService;

  public EmployeeFacadeImpl(final EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Employee> getEmployees() {
    return employeeService.getEmployees();
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Employee getEmployee(final long subjectId) {
    return employeeService.getEmployee(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Employee createEmployee(final Employee employee) throws UserAlreadyExists {
    return employeeService.createEmployee(employee);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Employee updateEmployee(final long subjectId, final Employee employee) {
    return employeeService.updateEmployee(subjectId, employee);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void deleteEmployee(final long subjectId) {
    employeeService.deleteEmployee(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Manager setManagerToEmployee(final long employeeId, final Manager manager) {
    return employeeService.setManagerToEmployee(employeeId, manager);
  }
}
