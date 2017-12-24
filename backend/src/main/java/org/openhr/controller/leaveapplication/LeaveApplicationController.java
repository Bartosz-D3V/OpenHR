package org.openhr.controller.leaveapplication;

import org.hibernate.HibernateException;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.process.TaskDefinition;
import org.openhr.enumeration.Role;
import org.openhr.exception.ApplicationDoesNotExistException;
import org.openhr.exception.SubjectDoesNotExistException;
import org.openhr.facade.leaveapplication.LeaveApplicationFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
  public LeaveApplication getLeaveApplication(@RequestParam final long applicationId) throws HibernateException,
    ApplicationDoesNotExistException {
    return leaveApplicationFacade.getLeaveApplication(applicationId);
  }

  @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public LeaveApplication createLeaveApplication(@RequestParam final long subjectId,
                                                 @RequestBody final LeaveApplication leaveApplication)
    throws HibernateException,
    SubjectDoesNotExistException {
    return leaveApplicationFacade.createLeaveApplication(subjectId, leaveApplication);
  }

  @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
  public LeaveApplication updateLeaveApplication(@RequestBody final LeaveApplication leaveApplication)
    throws HibernateException, ApplicationDoesNotExistException {
    return leaveApplicationFacade.updateLeaveApplication(leaveApplication);
  }

  @RequestMapping(value = "/reject", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void rejectLeaveApplication(@RequestParam final String taskId,
                                     @RequestBody final Role role) throws HibernateException {
    leaveApplicationFacade.rejectLeaveApplication(role, taskId);
  }

  @RequestMapping(value = "/approve", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void approveLeaveApplication(@RequestParam final String taskId,
                                      @RequestBody final Role role) throws HibernateException {
    leaveApplicationFacade.approveLeaveApplication(role, taskId);
  }

  @RequestMapping(value = "/tasks/{processInstanceId}", method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE})
  public List<TaskDefinition> getProcessTasks(@PathVariable final String processInstanceId) {
    return leaveApplicationFacade.getProcessTasks(processInstanceId);
  }

  @RequestMapping(value = "processes", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  public List<String> getActiveProcessesId() {
    return leaveApplicationFacade.getActiveProcessesId();
  }

}
