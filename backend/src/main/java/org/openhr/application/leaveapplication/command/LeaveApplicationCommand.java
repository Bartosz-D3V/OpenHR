package org.openhr.application.leaveapplication.command;

import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.common.domain.process.TaskDefinition;
import org.openhr.common.domain.subject.Subject;

import java.util.List;

public interface LeaveApplicationCommand {

  String startLeaveApplicationProcess(Subject subject, LeaveApplication leaveApplication);

  void rejectLeaveApplicationByManager(String processInstanceId, long applicationId);

  void approveLeaveApplicationByManager(String processInstanceId, long applicationId);

  void rejectLeaveApplicationByHr(String processInstanceId);

  void approveLeaveApplicationByHr(String processInstanceId);

  List<TaskDefinition> getProcessTasks(String processInstanceId);

  List<String> getActiveProcessesId();
}
