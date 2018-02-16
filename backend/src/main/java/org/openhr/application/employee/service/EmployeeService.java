package org.openhr.application.employee.service;

import org.openhr.common.domain.subject.Employee;

import java.util.List;

public interface EmployeeService {
  List<Employee> getEmployeesOfManager(long managerId);
}
