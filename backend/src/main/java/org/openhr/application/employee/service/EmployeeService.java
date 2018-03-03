package org.openhr.application.employee.service;

import org.openhr.application.employee.domain.Employee;
import org.openhr.application.manager.domain.Manager;

public interface EmployeeService {
  Employee getEmployee(long subjectId);

  Employee createEmployee(Employee employee);

  Employee updateEmployee(long subjectId, Employee employee);

  Manager setManagerToEmployee(long employeeId, Manager manager);
}
