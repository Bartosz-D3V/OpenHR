package org.openhr.application.employee.facade;

import org.openhr.application.employee.service.EmployeeService;
import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
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
  public Employee getEmployee(final long subjectId) {
    return employeeService.getEmployee(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Employee createEmployee(final Employee employee) {
    return employeeService.createEmployee(employee);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Employee updateEmployee(final long subjectId, final Employee employee) {
    return employeeService.updateEmployee(subjectId, employee);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Manager setManagerToEmployee(final long subjectId, final Employee employee) {
    return employeeService.setManagerToEmployee(subjectId, employee);
  }
}
