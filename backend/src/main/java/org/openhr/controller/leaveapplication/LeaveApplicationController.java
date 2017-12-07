package org.openhr.controller.leaveapplication;

import org.hibernate.HibernateException;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.enumeration.Role;
import org.openhr.exception.ApplicationDoesNotExistException;
import org.openhr.exception.SubjectDoesNotExistException;
import org.openhr.facade.leaveapplication.LeaveApplicationFacade;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/leave-application")
public class LeaveApplicationController {

  private final LeaveApplicationFacade leaveApplicationFacade;

  public LeaveApplicationController(LeaveApplicationFacade leaveApplicationFacade) {
    this.leaveApplicationFacade = leaveApplicationFacade;
  }

  @RequestMapping(method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void createLeaveApplication(@RequestParam final long applicationId) throws HibernateException,
    ApplicationDoesNotExistException {
    leaveApplicationFacade.getLeaveApplication(applicationId);
  }

  @Transactional
  @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void createLeaveApplication(@RequestParam final long subjectId,
                                     @RequestBody final LeaveApplication leaveApplication) throws HibernateException,
    SubjectDoesNotExistException {
    leaveApplicationFacade.createLeaveApplication(subjectId, leaveApplication);
  }

  @Transactional
  @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void updateLeaveApplication(@RequestBody final LeaveApplication leaveApplication) throws HibernateException,
    ApplicationDoesNotExistException {
    leaveApplicationFacade.updateLeaveApplication(leaveApplication);
  }

  @Transactional
  @RequestMapping(value = "/reject", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void rejectLeaveApplication(@RequestParam final long applicationId,
                                     @RequestBody final Role role) throws HibernateException,
    ApplicationDoesNotExistException {
    leaveApplicationFacade.rejectLeaveApplication(role, applicationId);
  }

  @Transactional
  @RequestMapping(value = "/approve", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void approveLeaveApplication(@RequestParam final long applicationId,
                                      @RequestBody final Role role) throws HibernateException,
    ApplicationDoesNotExistException {
    leaveApplicationFacade.approveLeaveApplication(role, applicationId);
  }

}
