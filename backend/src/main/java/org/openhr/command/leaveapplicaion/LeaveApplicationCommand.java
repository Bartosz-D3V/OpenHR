package org.openhr.command.leaveapplicaion;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.process.Task;
import org.openhr.domain.subject.Subject;

import java.util.List;

public interface LeaveApplicationCommand {

  void startLeaveApplicationProcess(Subject subject, LeaveApplication leaveApplication);

  List<Task> getProcessTasks(String processInstanceId);
}
