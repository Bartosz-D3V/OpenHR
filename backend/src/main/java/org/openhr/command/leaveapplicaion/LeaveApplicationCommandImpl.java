package org.openhr.command.leaveapplicaion;

import org.activiti.engine.RuntimeService;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.subject.Subject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LeaveApplicationCommandImpl implements LeaveApplicationCommand {

  private final RuntimeService runtimeService;

  public LeaveApplicationCommandImpl(final RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }

  @Override
  public void startLeaveApplicationProcess(final Subject subject, final LeaveApplication leaveApplication) {
    final Map<String, Object> parameters = new HashMap<>();
    parameters.put("email", subject.getContactInformation().getEmail());
    parameters.put("applicantName", subject.getLastName());
    parameters.put("subject", subject);
    parameters.put("leaveApplication", leaveApplication);
    runtimeService.startProcessInstanceByKey("leave-application", parameters);
  }
}
