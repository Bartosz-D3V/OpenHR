package org.openhr.command.leaveapplicaion;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.process.Task;

import java.util.List;

public interface LeaveApplicationCommand {

  void startLeaveApplicationProcess(LeaveApplication leaveApplication);

  List<Task> getProcessTasks(String processInstanceId);
}
