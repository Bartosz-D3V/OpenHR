package org.openhr.application.employee.dao;

import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;

public interface EmployeeDAO {
  Employee getEmployee(long subjectId);

  Employee createEmployee(Employee employee);

  Employee updateEmployee(long employeeId, Employee employee);

  Manager setManagerToEmployee(long employeeId, Manager manager);
}
