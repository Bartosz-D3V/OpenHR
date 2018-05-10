package org.openhr.application.employee.service;

import java.util.List;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.manager.domain.Manager;
import org.openhr.common.exception.UserAlreadyExists;

public interface EmployeeService {
  List<Employee> getEmployees();

  Employee getEmployee(long subjectId);

  Employee createEmployee(Employee employee) throws UserAlreadyExists;

  Employee updateEmployee(long subjectId, Employee employee);

  void deleteEmployee(long subjectId);

  Manager setManagerToEmployee(long employeeId, Manager manager);
}
