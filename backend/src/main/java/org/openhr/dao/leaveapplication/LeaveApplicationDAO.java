package org.openhr.dao.leaveapplication;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.subject.Subject;
import org.openhr.exception.ApplicationDoesNotExistException;

public interface LeaveApplicationDAO {

  LeaveApplication getLeaveApplication(long applicationId) throws ApplicationDoesNotExistException;

  void createLeaveApplication(Subject subject, LeaveApplication leaveApplication);

  LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication) throws ApplicationDoesNotExistException;

}
