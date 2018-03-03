package org.openhr.application.employee.controller;

import org.openhr.application.employee.facade.EmployeeFacade;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.manager.domain.Manager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {
  private final EmployeeFacade employeeFacade;

  public EmployeeController(final EmployeeFacade employeeFacade) {
    this.employeeFacade = employeeFacade;
  }

  @RequestMapping(value = "/{subjectId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Employee getEmployee(@PathVariable final long subjectId) {
    return employeeFacade.getEmployee(subjectId);
  }

  @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE},
    consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(code = HttpStatus.CREATED)
  public Employee createEmployee(@RequestBody final Employee employee) {
    return employeeFacade.createEmployee(employee);
  }

  @RequestMapping(value = "/{subjectId}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Employee updateEmployee(@PathVariable final long subjectId, @RequestBody final Employee employee) {
    return employeeFacade.updateEmployee(subjectId, employee);
  }

  @RequestMapping(value = "/{employeeId}/manager-assignment", method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Manager setManagerToEmployee(@PathVariable final long employeeId, @RequestBody final Manager manager) {
    return employeeFacade.setManagerToEmployee(employeeId, manager);
  }
}
