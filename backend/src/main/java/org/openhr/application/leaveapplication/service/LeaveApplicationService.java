package org.openhr.application.leaveapplication.service;

import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.ValidationException;

import java.util.List;

public interface LeaveApplicationService {

  LeaveApplication getLeaveApplication(long applicationId) throws ApplicationDoesNotExistException;

  LeaveApplication createLeaveApplication(Subject subject, LeaveApplication leaveApplication) throws ValidationException;

  LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication) throws ApplicationDoesNotExistException;

  void rejectLeaveApplicationByManager(long applicationId) throws ApplicationDoesNotExistException;

  void approveLeaveApplicationByManager(long applicationId) throws ApplicationDoesNotExistException;

  void rejectLeaveApplicationByHr(long applicationId) throws ApplicationDoesNotExistException;

  void approveLeaveApplicationByHr(long applicationId) throws ApplicationDoesNotExistException;

  List<LeaveApplication> getAwaitingForManagerLeaveApplications(long subjectId);

  List<LeaveType> getLeaveTypes();
}
