package org.openhr.command.leaveapplicaion;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.process.TaskDefinition;
import org.openhr.enumeration.Role;

import java.util.List;

public interface LeaveApplicationCommand {

  void startLeaveApplicationProcess(LeaveApplication leaveApplication);

  void rejectLeaveApplication(Role role, String processInstanceId);

  void approveLeaveApplication(Role role, String processInstanceId);

  List<TaskDefinition> getProcessTasks(String processInstanceId);

  List<String> getActiveProcessesId();
}
