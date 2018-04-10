package org.openhr.application.leaveapplication.dao;

import java.util.List;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ApplicationDoesNotExistException;

public interface LeaveApplicationDAO {

  LeaveApplication getLeaveApplication(long leaveApplicationId)
      throws ApplicationDoesNotExistException;

  LeaveApplication createLeaveApplication(Subject subject, LeaveApplication leaveApplication);

  LeaveApplication updateLeaveApplication(
      long leaveApplicationId, LeaveApplication leaveApplication)
      throws ApplicationDoesNotExistException;

  long getLeaveApplicationIdByProcessId(String processInstanceId);

  Subject getApplicationApplicant(long applicationId);

  Subject getApplicationAssignee(long applicationId);

  List<LeaveType> getLeaveTypes();
}
