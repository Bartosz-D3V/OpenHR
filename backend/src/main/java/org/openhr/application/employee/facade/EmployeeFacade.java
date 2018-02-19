package org.openhr.application.employee.facade;

import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.openhr.common.domain.subject.Subject;

import java.util.List;

public interface EmployeeFacade {
  Employee createEmployee(Employee employee);

  Manager setEmployeeManager(long subjectId, Employee employee);
}
