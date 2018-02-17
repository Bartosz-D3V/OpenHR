package org.openhr.application.manager.controller;

import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.application.manager.facade.ManagerFacade;
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
import java.util.Set;

@RestController
@RequestMapping(value = "/managers")
public class ManagerController {
  private final ManagerFacade managerFacade;

  public ManagerController(final ManagerFacade managerFacade) {
    this.managerFacade = managerFacade;
  }

  @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public Manager createManager(@RequestBody final Manager manager) {
    return managerFacade.createManager(manager);
  }

  @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
  public void updateManager(@RequestBody final Manager manager) throws SubjectDoesNotExistException {
    managerFacade.updateManager(manager);
  }

  @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<Manager> getManagers() {
    return managerFacade.getManagers();
  }

  @RequestMapping(value = "employees", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public Set<Employee> getEmployees(@RequestParam final long managerId) throws SubjectDoesNotExistException {
    return managerFacade.getEmployees(managerId);
  }

  @RequestMapping(value = "/{managerId}/employee-assignment", method = RequestMethod.POST,
    consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void addEmployeeToManager(@RequestBody final Employee employee, @PathVariable final long managerId)
    throws SubjectDoesNotExistException {
    managerFacade.addEmployeeToManager(employee, managerId);
  }
}
