package org.openhr.application.delegation.controller;

import java.util.List;
import org.openhr.application.delegation.domain.DelegationApplication;
import org.openhr.application.delegation.facade.DelegationApplicationFacade;
import org.openhr.common.domain.country.Country;
import org.openhr.common.exception.SubjectDoesNotExistException;
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
@RequestMapping(value = "/delegations")
public class DelegationApplicationController {
  private final DelegationApplicationFacade delegationApplicationFacade;

  public DelegationApplicationController(
      final DelegationApplicationFacade delegationApplicationFacade) {
    this.delegationApplicationFacade = delegationApplicationFacade;
  }

  @RequestMapping(
    value = "/countries",
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<Country> getCountries() {
    return delegationApplicationFacade.getCountries();
  }

  @RequestMapping(
    method = RequestMethod.POST,
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public DelegationApplication startDelegationApplicationProcess(
      @RequestParam final long subjectId,
      @RequestBody final DelegationApplication delegationApplication)
      throws SubjectDoesNotExistException {
    return delegationApplicationFacade.startDelegationApplicationProcess(
        subjectId, delegationApplication);
  }

  @RequestMapping(
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<DelegationApplication> getSubjectsDelegationApplications(
      @RequestParam final long subjectId) {
    return delegationApplicationFacade.getSubjectsDelegationApplications(subjectId);
  }

  @RequestMapping(
    value = "/{delegationApplicationId}",
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public DelegationApplication getDelegationApplication(
      @PathVariable final long delegationApplicationId) {
    return delegationApplicationFacade.getDelegationApplication(delegationApplicationId);
  }

  @RequestMapping(
    value = "/awaiting",
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<DelegationApplication> getAwaitingForActionDelegationApplications(
      @RequestParam final long subjectId) {
    return delegationApplicationFacade.getAwaitingForActionDelegationApplications(subjectId);
  }

  @RequestMapping(
    method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public DelegationApplication updateDelegationApplication(
      @RequestParam final String processInstanceId,
      @RequestBody final DelegationApplication delegationApplication) {
    return delegationApplicationFacade.updateDelegationApplication(
        processInstanceId, delegationApplication);
  }

  @RequestMapping(value = "/manager-reject", method = RequestMethod.PUT)
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void rejectDelegationApplicationByManager(@RequestParam final String processInstanceId) {
    delegationApplicationFacade.rejectDelegationApplicationByManager(processInstanceId);
  }

  @RequestMapping(value = "/manager-approve", method = RequestMethod.PUT)
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void approveDelegationApplicationByManager(@RequestParam final String processInstanceId) {
    delegationApplicationFacade.approveDelegationApplicationByManager(processInstanceId);
  }

  @RequestMapping(value = "/hr-reject", method = RequestMethod.PUT)
  @PreAuthorize("hasRole('ROLE_HRTEAMMEMBER')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void rejectDelegationApplicationByHr(@RequestParam final String processInstanceId) {
    delegationApplicationFacade.rejectDelegationApplicationByHr(processInstanceId);
  }

  @RequestMapping(value = "/hr-approve", method = RequestMethod.PUT)
  @PreAuthorize("hasRole('ROLE_HRTEAMMEMBER')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void approveDelegationApplicationByHr(@RequestParam final String processInstanceId) {
    delegationApplicationFacade.approveDelegationApplicationByHr(processInstanceId);
  }
}
