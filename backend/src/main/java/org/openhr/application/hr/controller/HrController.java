package org.openhr.application.hr.controller;

import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.hr.facade.HrFacade;
import org.openhr.common.exception.SubjectDoesNotExistException;
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
@RequestMapping(value = "/hr-team-members")
public class HrController {
  private final HrFacade hrFacade;

  public HrController(final HrFacade hrFacade) {
    this.hrFacade = hrFacade;
  }

  @RequestMapping(value = "/{subjectId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public HrTeamMember getHrTeamMember(@PathVariable final long subjectId) {
    return hrFacade.getHrTeamMember(subjectId);
  }

  @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public HrTeamMember addHrTeamMember(@RequestBody final HrTeamMember hrTeamMember) {
    return hrFacade.addHrTeamMember(hrTeamMember);
  }

  @RequestMapping(value = "/{subjectId}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.OK)
  public HrTeamMember updateHrTeamMember(@PathVariable final long subjectId,
                                         @RequestBody final HrTeamMember hrTeamMember) throws SubjectDoesNotExistException {
    return hrFacade.updateHrTeamMember(subjectId, hrTeamMember);
  }
}
