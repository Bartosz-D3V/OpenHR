package org.openhr.application.employee.dao;

import org.openhr.application.employee.domain.Employee;
import org.openhr.application.manager.domain.Manager;

public interface EmployeeDAO {
  Employee getEmployee(long subjectId);

  Employee createEmployee(Employee employee);

  Employee updateEmployee(long employeeId, Employee employee);

  void deleteEmployee(Employee employee);

  Manager setManagerToEmployee(long employeeId, Manager manager);
}
