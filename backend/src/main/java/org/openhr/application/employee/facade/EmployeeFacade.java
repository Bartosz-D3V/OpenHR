package org.openhr.application.employee.facade;

import org.openhr.common.domain.subject.Employee;

import java.util.List;

public interface EmployeeFacade {
  List<Employee> getEmployeesOfManager(long managerId);
}
