package org.openhr.application.employee.controller;

import org.openhr.application.employee.facade.EmployeeFacade;
import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {
  private final EmployeeFacade employeeFacade;

  public EmployeeController(final EmployeeFacade employeeFacade) {
    this.employeeFacade = employeeFacade;
  }

  @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<Employee> getEmployeesOfManager(@RequestParam final long managerId) {
    return employeeFacade.getEmployeesOfManager(managerId);
  }

  @RequestMapping(value = "/{employeeId}/manager-assignment", method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Manager setEmployeeManager(@PathVariable final long employeeId, @RequestBody final Employee employee) {
    return employeeFacade.setEmployeeManager(employeeId, employee);
  }
}
