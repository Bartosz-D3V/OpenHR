package org.openhr.application.leaveapplication.service;

import java.util.List;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.ValidationException;
import org.openhr.common.util.iterable.LocalDateRange;

public interface LeaveApplicationService {

  LeaveApplication getLeaveApplication(long leaveApplicationId)
      throws ApplicationDoesNotExistException;

  List<LeaveApplication> getSubjectsLeaveApplications(long subjectId);

  LeaveApplication createLeaveApplication(Subject subject, LeaveApplication leaveApplication)
      throws ValidationException;

  LeaveApplication updateLeaveApplication(
      long leaveApplicationId, LeaveApplication leaveApplication)
      throws ApplicationDoesNotExistException;

  void rejectLeaveApplicationByManager(long applicationId) throws ApplicationDoesNotExistException;

  void approveLeaveApplicationByManager(long applicationId)
      throws ApplicationDoesNotExistException, ValidationException, SubjectDoesNotExistException;

  void rejectLeaveApplicationByHr(long applicationId) throws ApplicationDoesNotExistException;

  void approveLeaveApplicationByHr(long applicationId)
      throws ApplicationDoesNotExistException, SubjectDoesNotExistException, ValidationException;

  void terminateLeaveApplication(long applicationId) throws ApplicationDoesNotExistException;

  List<LeaveApplication> getAwaitingForActionLeaveApplications(long subjectId);

  Subject getApplicationApplicant(long applicationId);

  Subject getApplicationAssignee(long applicationId);

  List<LeaveType> getLeaveTypes();

  LeaveType getLeaveTypeById(long leaveTypeId);

  long getLeaveApplicationIdByProcessId(String processInstanceId);

  List<LeaveApplication> getSubjectsLeaveApplicationsInRange(
      LocalDateRange dateRange, long subjectId);
}
