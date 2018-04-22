package org.openhr.application.leaveapplication.facade;

import java.io.IOException;
import java.util.List;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.ValidationException;

public interface LeaveApplicationFacade {

  LeaveApplication getLeaveApplication(long leaveApplicationId)
      throws ApplicationDoesNotExistException;

  List<LeaveApplication> getSubjectsLeaveApplications(long subjectId);

  LeaveApplication createLeaveApplication(long subjectId, LeaveApplication leaveApplication)
      throws SubjectDoesNotExistException, ValidationException, ApplicationDoesNotExistException;

  LeaveApplication updateLeaveApplication(
      long leaveApplicationId, LeaveApplication leaveApplication)
      throws ApplicationDoesNotExistException;

  void rejectLeaveApplicationByManager(String processInstanceId);

  void approveLeaveApplicationByManager(String processInstanceId)
      throws ApplicationDoesNotExistException, SubjectDoesNotExistException;

  void rejectLeaveApplicationByHR(String processInstanceId)
      throws SubjectDoesNotExistException, ApplicationDoesNotExistException;

  void approveLeaveApplicationByHR(String processInstanceId)
      throws ApplicationDoesNotExistException;

  List<LeaveApplication> getAwaitingForActionLeaveApplications(long subjectId);

  List<LeaveType> getLeaveTypes();

  byte[] getLeaveApplicationICSFile(long leaveApplicationId)
      throws ApplicationDoesNotExistException, IOException;
}
