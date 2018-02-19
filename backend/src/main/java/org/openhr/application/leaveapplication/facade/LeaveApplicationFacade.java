package org.openhr.application.leaveapplication.facade;

import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.common.domain.process.TaskDefinition;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.ValidationException;

import java.util.List;

public interface LeaveApplicationFacade {

  LeaveApplication getLeaveApplication(long applicationId) throws ApplicationDoesNotExistException;

  LeaveApplication createLeaveApplication(long subjectId, LeaveApplication leaveApplication)
    throws SubjectDoesNotExistException, ValidationException, ApplicationDoesNotExistException;

  LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication) throws ApplicationDoesNotExistException;

  void rejectLeaveApplicationByManager(String processInstanceId);

  void approveLeaveApplicationByManager(String processInstanceId);

  List<LeaveApplication> getAwaitingForManagerLeaveApplications(long subjectId);

  List<TaskDefinition> getProcessTasks(String processInstanceId);

  List<String> getActiveProcessesId();

  List<LeaveType> getLeaveTypes();
}
