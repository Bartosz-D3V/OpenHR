package org.openhr.application.employee.facade;

import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;

import java.util.List;

public interface EmployeeFacade {
  List<Employee> getEmployeesOfManager(long managerId);

  Manager setEmployeeManager(long employeeId, Employee employee);

  Employee createEmployee(Employee employee);
}
