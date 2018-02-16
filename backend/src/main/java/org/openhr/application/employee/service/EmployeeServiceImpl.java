package org.openhr.application.employee.service;

import org.openhr.application.employee.repository.EmployeeRepository;
import org.openhr.common.domain.subject.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
  private final EmployeeRepository employeeRepository;

  public EmployeeServiceImpl(final EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Employee> getEmployeesOfManager(final long managerId) {
    return employeeRepository.getEmployeesOfManager(managerId);
  }
}
