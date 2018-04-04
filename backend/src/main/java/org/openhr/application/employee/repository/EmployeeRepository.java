package org.openhr.application.employee.repository;

import org.openhr.application.employee.dao.EmployeeDAO;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.manager.domain.Manager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EmployeeRepository {
  private final EmployeeDAO employeeDAO;

  public EmployeeRepository(final EmployeeDAO employeeDAO) {
    this.employeeDAO = employeeDAO;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Employee getEmployee(final long subjectId) {
    return employeeDAO.getEmployee(subjectId);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public Employee createEmployee(final Employee employee) {
    return employeeDAO.createEmployee(employee);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public Employee updateEmployee(final long subjectId, final Employee employee) {
    return employeeDAO.updateEmployee(subjectId, employee);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public Manager setManagerToEmployee(final long employeeId, final Manager manager) {
    return employeeDAO.setManagerToEmployee(employeeId, manager);
  }
}
