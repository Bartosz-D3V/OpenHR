package org.openhr.application.leaveapplication.command;

import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.common.domain.process.TaskDefinition;
import org.openhr.application.leaveapplication.enumeration.Role;

import java.util.List;

public interface LeaveApplicationCommand {

  String startLeaveApplicationProcess(LeaveApplication leaveApplication);

  void rejectLeaveApplication(Role role, String processInstanceId);

  void approveLeaveApplication(Role role, String processInstanceId);

  List<TaskDefinition> getProcessTasks(String processInstanceId);

  List<String> getActiveProcessesId();
}
