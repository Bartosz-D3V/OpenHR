package org.openhr.application.employee.service;

import org.openhr.application.employee.dao.EmployeeDAO;
import org.openhr.application.employee.repository.EmployeeRepository;
import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.openhr.common.domain.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
  private final EmployeeDAO employeeDAO;
  private final EmployeeRepository employeeRepository;

  public EmployeeServiceImpl(final EmployeeDAO employeeDAO,
                             final EmployeeRepository employeeRepository) {
    this.employeeDAO = employeeDAO;
    this.employeeRepository = employeeRepository;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Employee createEmployee(final Employee employee) {
    return employeeDAO.createEmployee(employee);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Manager setEmployeeManager(final long subjectId, final Employee employee) {
    return employeeDAO.setEmployeeManager(subjectId, employee);
  }
}
