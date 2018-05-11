package org.openhr.application.manager.controller;

import java.util.List;
import java.util.Set;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.manager.facade.ManagerFacade;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserAlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/managers")
public class ManagerController {
  private final ManagerFacade managerFacade;

  public ManagerController(final ManagerFacade managerFacade) {
    this.managerFacade = managerFacade;
  }

  @RequestMapping(
    value = "/{subjectId}",
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Manager getManager(@PathVariable final long subjectId)
      throws SubjectDoesNotExistException {
    return managerFacade.getManager(subjectId);
  }

  @RequestMapping(
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<Manager> getManagers() {
    return managerFacade.getManagers();
  }

  @RequestMapping(
    value = "/{subjectId}/employees",
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Set<Employee> getEmployees(@PathVariable final long subjectId)
      throws SubjectDoesNotExistException {
    return managerFacade.getManagersEmployees(subjectId);
  }

  @RequestMapping(
    method = RequestMethod.POST,
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public Manager addManager(@RequestBody final Manager manager) throws UserAlreadyExists {
    return managerFacade.addManager(manager);
  }

  @RequestMapping(
    value = "/{subjectId}",
    method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Manager updateManager(
      @PathVariable final long subjectId, @RequestBody final Manager manager)
      throws SubjectDoesNotExistException {
    return managerFacade.updateManager(subjectId, manager);
  }

  @RequestMapping(value = "/{subjectId}", method = RequestMethod.DELETE)
  @PreAuthorize("hasRole('ROLE_HRTEAMMEMBER')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteManager(@PathVariable final long subjectId)
      throws SubjectDoesNotExistException {
    managerFacade.deleteManager(subjectId);
  }

  @RequestMapping(value = "/employee-assignment", method = RequestMethod.POST)
  @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_HRTEAMMEMBER')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void addEmployeeToManager(
      @RequestParam final long managerId, @RequestParam final long subjectId)
      throws SubjectDoesNotExistException {
    managerFacade.addEmployeeToManager(managerId, subjectId);
  }

  @RequestMapping(
    value = "{managerId}/hr-assignment",
    method = RequestMethod.PUT,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_HRTEAMMEMBER')")
  @ResponseBody
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Manager setManagerToEmployee(
      @PathVariable final long managerId, @RequestParam final long hrTeamMemberId)
      throws SubjectDoesNotExistException {
    return managerFacade.setHrToManager(managerId, hrTeamMemberId);
  }
}
