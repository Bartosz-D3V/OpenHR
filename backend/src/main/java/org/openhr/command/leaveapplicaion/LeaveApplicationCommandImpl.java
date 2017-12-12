package org.openhr.command.leaveapplicaion;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.process.Task;
import org.openhr.domain.subject.Subject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LeaveApplicationCommandImpl implements LeaveApplicationCommand {

  private final RuntimeService runtimeService;
  private final TaskService taskService;

  public LeaveApplicationCommandImpl(final RuntimeService runtimeService, final TaskService taskService) {
    this.runtimeService = runtimeService;
    this.taskService = taskService;
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

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Task> getProcessTasks(final String processInstanceId) {
    return taskService.createTaskQuery()
      .processInstanceId(processInstanceId)
      .list()
      .stream()
      .map(task -> new Task(task.getId(), task.getName(), task.getProcessInstanceId()))
      .collect(Collectors.toList());
  }
}
