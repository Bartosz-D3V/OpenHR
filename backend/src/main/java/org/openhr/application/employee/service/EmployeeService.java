package org.openhr.application.employee.service;

import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;

import java.util.List;

public interface EmployeeService {
  List<Employee> getEmployeesOfManager(long managerId);

  Manager setEmployeeManager(long employeeId, Employee employee);

  Employee createEmployee(Employee employee);
}
