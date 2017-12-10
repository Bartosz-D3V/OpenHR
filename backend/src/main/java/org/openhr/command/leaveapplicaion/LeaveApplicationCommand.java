package org.openhr.command.leaveapplicaion;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.subject.Subject;

public interface LeaveApplicationCommand {

  void startLeaveApplicationProcess(Subject subject, LeaveApplication leaveApplication);

}
