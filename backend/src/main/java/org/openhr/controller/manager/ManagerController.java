package org.openhr.controller.manager;

import org.openhr.domain.subject.Employee;
import org.openhr.domain.subject.Manager;
import org.openhr.exception.SubjectDoesNotExistException;
import org.openhr.facade.manager.ManagerFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/manager")
public class ManagerController {
  private final ManagerFacade managerFacade;

  public ManagerController(final ManagerFacade managerFacade) {
    this.managerFacade = managerFacade;
  }

  @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
  public Manager addManager(@RequestBody final Manager manager) {
    return managerFacade.addManager(manager);
  }

  @RequestMapping(value = "employees", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  public Set<Employee> getEmployees(@RequestParam final long managerId) throws SubjectDoesNotExistException {
    return managerFacade.getEmployees(managerId);
  }
}
