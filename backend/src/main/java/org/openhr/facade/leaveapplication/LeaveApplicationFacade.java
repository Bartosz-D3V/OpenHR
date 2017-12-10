package org.openhr.facade.leaveapplication;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.process.Task;
import org.openhr.enumeration.Role;
import org.openhr.exception.ApplicationDoesNotExistException;
import org.openhr.exception.SubjectDoesNotExistException;

import java.util.List;

public interface LeaveApplicationFacade {

  LeaveApplication getLeaveApplication(long applicationId) throws ApplicationDoesNotExistException;

  LeaveApplication createLeaveApplication(long subjectId, LeaveApplication leaveApplication) throws SubjectDoesNotExistException;

  LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication) throws ApplicationDoesNotExistException;

  void rejectLeaveApplication(Role role, long applicationId) throws ApplicationDoesNotExistException;

  void approveLeaveApplication(Role role, long applicationId) throws ApplicationDoesNotExistException;

  List<Task> getProcessTasks(String processInstanceId);
}
