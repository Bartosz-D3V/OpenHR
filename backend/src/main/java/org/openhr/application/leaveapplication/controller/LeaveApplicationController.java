package org.openhr.application.leaveapplication.controller;

import java.util.List;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.application.leaveapplication.facade.LeaveApplicationFacade;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.ValidationException;
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
@RequestMapping(value = "/leave-applications")
public class LeaveApplicationController {

  private final LeaveApplicationFacade leaveApplicationFacade;

  public LeaveApplicationController(final LeaveApplicationFacade leaveApplicationFacade) {
    this.leaveApplicationFacade = leaveApplicationFacade;
  }

  @RequestMapping(
    value = "/{leaveApplicationId}",
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  public LeaveApplication getLeaveApplication(@PathVariable final long leaveApplicationId)
      throws ApplicationDoesNotExistException {
    return leaveApplicationFacade.getLeaveApplication(leaveApplicationId);
  }

  @RequestMapping(
    method = RequestMethod.POST,
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public LeaveApplication createLeaveApplication(
      @RequestParam final long subjectId, @RequestBody final LeaveApplication leaveApplication)
      throws SubjectDoesNotExistException, ValidationException, ApplicationDoesNotExistException {
    return leaveApplicationFacade.createLeaveApplication(subjectId, leaveApplication);
  }

  @RequestMapping(
    value = "/{leaveApplicationId}",
    method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public LeaveApplication updateLeaveApplication(
      @PathVariable final long leaveApplicationId,
      @RequestBody final LeaveApplication leaveApplication)
      throws ApplicationDoesNotExistException {
    return leaveApplicationFacade.updateLeaveApplication(leaveApplicationId, leaveApplication);
  }

  @RequestMapping(
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<LeaveApplication> getSubjectsLeaveApplications(@RequestParam final long subjectId) {
    return leaveApplicationFacade.getSubjectsLeaveApplications(subjectId);
  }

  @RequestMapping(
    value = "/manager-reject",
    method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE}
  )
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void rejectLeaveApplicationByManager(
      @RequestParam final String processInstanceId,
      @RequestBody(required = false) final String refusalReason)
      throws ApplicationDoesNotExistException {
    leaveApplicationFacade.rejectLeaveApplicationByManager(processInstanceId, refusalReason);
  }

  @RequestMapping(
    value = "/manager-approve",
    method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE}
  )
  @PreAuthorize("hasRole('ROLE_MANAGER')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void approveLeaveApplicationByManager(@RequestParam final String processInstanceId)
      throws ApplicationDoesNotExistException, SubjectDoesNotExistException {
    leaveApplicationFacade.approveLeaveApplicationByManager(processInstanceId);
  }

  @RequestMapping(
    value = "/hr-reject",
    method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE}
  )
  @PreAuthorize("hasRole('ROLE_HRTEAMMEMBER')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void rejectLeaveApplicationByHR(
      @RequestParam final String processInstanceId,
      @RequestBody(required = false) final String refusalReason)
      throws ApplicationDoesNotExistException, SubjectDoesNotExistException {
    leaveApplicationFacade.rejectLeaveApplicationByHR(processInstanceId, refusalReason);
  }

  @RequestMapping(
    value = "/hr-approve",
    method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE}
  )
  @PreAuthorize("hasRole('ROLE_HRTEAMMEMBER')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void approveLeaveApplicationByHR(@RequestParam final String processInstanceId)
      throws ApplicationDoesNotExistException {
    leaveApplicationFacade.approveLeaveApplicationByHR(processInstanceId);
  }

  @RequestMapping(
    value = "/awaiting",
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<LeaveApplication> getAwaitingForActionLeaveApplications(
      @RequestParam final long subjectId) {
    return leaveApplicationFacade.getAwaitingForActionLeaveApplications(subjectId);
  }

  @RequestMapping(
    value = "/types",
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  @ResponseStatus(value = HttpStatus.OK)
  public List<LeaveType> getLeaveTypes() {
    return leaveApplicationFacade.getLeaveTypes();
  }
}
