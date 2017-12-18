package org.openhr.command.leaveapplicaion;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.process.TaskDefinition;
import org.openhr.enumeration.Role;

import java.util.List;

public interface LeaveApplicationCommand {

  void startLeaveApplicationProcess(LeaveApplication leaveApplication);

  void rejectLeaveApplication(Role role, String taskId);

  void approveLeaveApplication(Role role, String taskId);

  List<TaskDefinition> getProcessTasks(String processInstanceId);
}
