package org.openhr.application.leaveapplication.controller;

import org.hibernate.HibernateException;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.common.domain.process.TaskDefinition;
import org.openhr.application.leaveapplication.enumeration.Role;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.application.leaveapplication.facade.LeaveApplicationFacade;
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

  @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public LeaveApplication createLeaveApplication(@RequestParam final long subjectId,
                                                 @RequestBody final LeaveApplication leaveApplication)
    throws HibernateException,
    SubjectDoesNotExistException, ApplicationDoesNotExistException {
    return leaveApplicationFacade.createLeaveApplication(subjectId, leaveApplication);
  }

  @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  public LeaveApplication updateLeaveApplication(@RequestBody final LeaveApplication leaveApplication)
    throws HibernateException, ApplicationDoesNotExistException {
    return leaveApplicationFacade.updateLeaveApplication(leaveApplication);
  }

  @RequestMapping(value = "/reject", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void rejectLeaveApplication(@RequestParam final String processInstanceId,
                                     @RequestBody final Role role) throws HibernateException {
    leaveApplicationFacade.rejectLeaveApplication(role, processInstanceId);
  }

  @RequestMapping(value = "/approve", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void approveLeaveApplication(@RequestParam final String processInstanceId,
                                      @RequestBody final Role role) throws HibernateException {
    leaveApplicationFacade.approveLeaveApplication(role, processInstanceId);
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
