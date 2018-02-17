package org.openhr.application.employee.facade;

import org.openhr.application.employee.service.EmployeeService;
import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeFacadeImpl implements EmployeeFacade {
  private final EmployeeService employeeService;

  public EmployeeFacadeImpl(final EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Employee> getEmployeesOfManager(final long managerId) {
    return employeeService.getEmployeesOfManager(managerId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Manager setEmployeeManager(final long employeeId, final Employee employee) {
    return employeeService.setEmployeeManager(employeeId, employee);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Employee createEmployee(final Employee employee) {
    return employeeService.createEmployee(employee);
  }
}
