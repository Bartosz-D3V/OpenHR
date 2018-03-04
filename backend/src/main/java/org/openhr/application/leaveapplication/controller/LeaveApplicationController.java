package org.openhr.application.leaveapplication.controller;

import org.hibernate.HibernateException;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.application.leaveapplication.facade.LeaveApplicationFacade;
import org.openhr.common.domain.process.TaskDefinition;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.ValidationException;
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
@RequestMapping(value = "/leave-application")
public class LeaveApplicationController {

  private final LeaveApplicationFacade leaveApplicationFacade;

  public LeaveApplicationController(final LeaveApplicationFacade leaveApplicationFacade) {
    this.leaveApplicationFacade = leaveApplicationFacade;
  }

  @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public LeaveApplication getLeaveApplication(@RequestParam final long applicationId) throws HibernateException,
    ApplicationDoesNotExistException {
    return leaveApplicationFacade.getLeaveApplication(applicationId);
  }

  @RequestMapping(value = "/{subjectId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<LeaveApplication> getSubjectsLeaveApplications(@PathVariable final long subjectId) {
    return leaveApplicationFacade.getSubjectsLeaveApplications(subjectId);
  }

  @RequestMapping(value = "/{subjectId}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public LeaveApplication createLeaveApplication(@PathVariable final long subjectId,
                                                 @RequestBody final LeaveApplication leaveApplication)
    throws SubjectDoesNotExistException, ValidationException, ApplicationDoesNotExistException {
    return leaveApplicationFacade.createLeaveApplication(subjectId, leaveApplication);
  }

  @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public LeaveApplication updateLeaveApplication(@RequestBody final LeaveApplication leaveApplication)
    throws ApplicationDoesNotExistException {
    return leaveApplicationFacade.updateLeaveApplication(leaveApplication);
  }

  @RequestMapping(value = "/manager-reject", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void rejectLeaveApplicationByManager(@RequestParam final String processInstanceId) {
    leaveApplicationFacade.rejectLeaveApplicationByManager(processInstanceId);
  }

  @RequestMapping(value = "/manager-approve", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void approveLeaveApplicationByManager(@RequestParam final String processInstanceId)
    throws ApplicationDoesNotExistException, SubjectDoesNotExistException {
    leaveApplicationFacade.approveLeaveApplicationByManager(processInstanceId);
  }

  @RequestMapping(value = "/hr-reject", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void rejectLeaveApplicationByHR(@RequestParam final String processInstanceId)
    throws ApplicationDoesNotExistException, SubjectDoesNotExistException {
    leaveApplicationFacade.rejectLeaveApplicationByHR(processInstanceId);
  }

  @RequestMapping(value = "/hr-approve", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void approveLeaveApplicationByHR(@RequestParam final String processInstanceId)
    throws ApplicationDoesNotExistException {
    leaveApplicationFacade.approveLeaveApplicationByHR(processInstanceId);
  }

  @RequestMapping(value = "/{subjectId}/awaiting", method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<LeaveApplication> getAwaitingForActionLeaveApplications(@PathVariable("subjectId") final long subjectId) {
    return leaveApplicationFacade.getAwaitingForActionLeaveApplications(subjectId);
  }

  @RequestMapping(value = "/tasks/{processInstanceId}", method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public List<TaskDefinition> getProcessTasks(@PathVariable final String processInstanceId) {
    return leaveApplicationFacade.getProcessTasks(processInstanceId);
  }

  @RequestMapping(value = "processes", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public List<String> getActiveProcessesId() {
    return leaveApplicationFacade.getActiveProcessesId();
  }

  @RequestMapping(value = "/types", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(value = HttpStatus.OK)
  public List<LeaveType> getLeaveTypes() {
    return leaveApplicationFacade.getLeaveTypes();
  }
}
