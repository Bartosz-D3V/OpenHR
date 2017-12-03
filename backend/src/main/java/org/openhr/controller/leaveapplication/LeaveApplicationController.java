package org.openhr.controller.leaveapplication;

import org.hibernate.HibernateException;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.facade.leaveapplication.LeaveApplicationFacade;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/leave-application")
public class LeaveApplicationController {

  private final LeaveApplicationFacade leaveApplicationFacade;

  public LeaveApplicationController(LeaveApplicationFacade leaveApplicationFacade) {
    this.leaveApplicationFacade = leaveApplicationFacade;
  }

  @Transactional
  @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void createLeaveApplication(@RequestBody final LeaveApplication leaveApplication) throws HibernateException {
    leaveApplicationFacade.createLeaveApplication(leaveApplication);
  }

}
