package org.openhr.application.employee.service;

import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.openhr.common.domain.subject.Subject;

import java.util.List;

public interface EmployeeService {
  List<Employee> getEmployeesOfManager(long managerId);

  Manager setEmployeeManager(long subjectId, Employee employee);
}
