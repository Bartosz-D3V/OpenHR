package org.openhr.application.leaveapplication.command;

import java.util.List;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.process.TaskDefinition;
import org.openhr.common.domain.subject.Subject;

public interface LeaveApplicationCommand {

  String startLeaveApplicationProcess(Subject subject, LeaveApplication leaveApplication);

  void rejectLeaveApplicationByManager(String processInstanceId, long applicationId);

  void approveLeaveApplicationByManager(String processInstanceId, long applicationId);

  void rejectLeaveApplicationByHr(String processInstanceId, User user);

  void approveLeaveApplicationByHr(String processInstanceId, User user);

  List<TaskDefinition> getProcessTasks(String processInstanceId);

  List<String> getActiveProcessesId();
}
