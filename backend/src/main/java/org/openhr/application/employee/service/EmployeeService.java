package org.openhr.application.employee.service;

import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;

public interface EmployeeService {
  Employee getEmployee(long subjectId);

  Employee createEmployee(Employee employee);

  Employee updateEmployee(long subjectId, Employee employee);

  Manager setManagerToEmployee(long employeeId, Manager manager);
}
