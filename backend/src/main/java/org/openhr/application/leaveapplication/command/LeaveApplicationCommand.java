package org.openhr.application.leaveapplication.command;

import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.common.domain.process.TaskDefinition;
import org.openhr.application.leaveapplication.enumeration.Role;

import java.util.List;

public interface LeaveApplicationCommand {

  String startLeaveApplicationProcess(Role role, LeaveApplication leaveApplication);

  void rejectLeaveApplicationByManager(String processInstanceId);

  void approveLeaveApplicationByManager(String processInstanceId);

  void rejectLeaveApplicationByHr(String processInstanceId);

  void approveLeaveApplicationByHr(String processInstanceId);

  List<TaskDefinition> getProcessTasks(String processInstanceId);

  List<String> getActiveProcessesId();
}
