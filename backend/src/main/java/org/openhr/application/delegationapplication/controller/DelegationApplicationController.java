package org.openhr.application.delegationapplication.controller;

import org.openhr.application.delegationapplication.domain.DelegationApplication;
import org.openhr.application.delegationapplication.facade.DelegationApplicationFacade;
import org.openhr.common.domain.country.Country;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.SubjectDoesNotExistException;
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
@RequestMapping(value = "/delegations")
public class DelegationApplicationController {
  private final DelegationApplicationFacade delegationApplicationFacade;

  public DelegationApplicationController(final DelegationApplicationFacade delegationApplicationFacade) {
    this.delegationApplicationFacade = delegationApplicationFacade;
  }

  @RequestMapping(value = "/countries", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<Country> getCountries() {
    return delegationApplicationFacade.getCountries();
  }

  @RequestMapping(value = "/{subjectId}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public DelegationApplication startDelegationApplicationProcess(@PathVariable final long subjectId,
                                                                 @RequestBody final DelegationApplication delegationApplication)
    throws SubjectDoesNotExistException {
    return delegationApplicationFacade.startDelegationApplicationProcess(subjectId, delegationApplication);
  }

  @RequestMapping(value = "/manager-reject", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void rejectDelegationApplicationByManager(@RequestParam final String processInstanceId) {
    delegationApplicationFacade.rejectDelegationApplicationByManager(processInstanceId);
  }

  @RequestMapping(value = "/manager-approve", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void approveDelegationApplicationByManager(@RequestParam final String processInstanceId)
    throws ApplicationDoesNotExistException, SubjectDoesNotExistException {
    delegationApplicationFacade.approveDelegationApplicationByManager(processInstanceId);
  }

  @RequestMapping(value = "/hr-reject", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void rejectDelegationApplicationByHr(@RequestParam final String processInstanceId)
    throws ApplicationDoesNotExistException, SubjectDoesNotExistException {
    delegationApplicationFacade.rejectDelegationApplicationByHr(processInstanceId);
  }

  @RequestMapping(value = "/hr-approve", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void approveDelegationApplicationByHr(@RequestParam final String processInstanceId)
    throws ApplicationDoesNotExistException {
    delegationApplicationFacade.approveDelegationApplicationByHr(processInstanceId);
  }
}
