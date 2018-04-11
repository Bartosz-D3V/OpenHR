package org.openhr.application.supervisor.controller;

import java.util.List;
import org.openhr.application.supervisor.facade.SupervisorFacade;
import org.openhr.common.domain.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/supervisors")
public class SupervisorController {

  private final SupervisorFacade supervisorFacade;

  public SupervisorController(final SupervisorFacade supervisorFacade) {
    this.supervisorFacade = supervisorFacade;
  }

  @RequestMapping(
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<Subject> getSupervisors() {
    return supervisorFacade.getSupervisors();
  }
}
