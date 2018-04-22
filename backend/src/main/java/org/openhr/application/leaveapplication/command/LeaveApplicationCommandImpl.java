package org.openhr.application.leaveapplication.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.common.domain.process.TaskDefinition;
import org.openhr.common.domain.subject.Subject;
import org.springframework.stereotype.Component;

@Component
public class LeaveApplicationCommandImpl implements LeaveApplicationCommand {

  private final RuntimeService runtimeService;
  private final TaskService taskService;

  public LeaveApplicationCommandImpl(
      final RuntimeService runtimeService, final TaskService taskService) {
    this.runtimeService = runtimeService;
    this.taskService = taskService;
  }

  @Override
  public String startLeaveApplicationProcess(
      final Subject subject, final LeaveApplication leaveApplication) {
    final Map<String, Object> parameters = new HashMap<>();
    parameters.put("subject", subject);
    parameters.put("userId", subject.getUser().getUserId());
    parameters.put("emailAddress", subject.getContactInformation().getEmail());
    parameters.put("leaveApplication", leaveApplication);
    parameters.put("applicationId", leaveApplication.getApplicationId());
    return runtimeService
        .startProcessInstanceByKey("leave-application", parameters)
        .getProcessInstanceId();
  }

  @Override
  public void rejectLeaveApplicationByManager(
      final String processInstanceId, final long applicationId) {
    final Map<String, Object> args = new HashMap<>();
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    args.put("approvedByManager", false);
    args.put("applicationId", applicationId);
    taskService.complete(task.getId(), args);
  }

  @Override
  public void approveLeaveApplicationByManager(
      final String processInstanceId, final long applicationId) {
    final Map<String, Object> args = new HashMap<>();
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    args.put("approvedByManager", true);
    args.put("applicationId", applicationId);
    taskService.complete(task.getId(), args);
  }

  @Override
  public void rejectLeaveApplicationByHr(final String processInstanceId) {
    final Map<String, Object> args = new HashMap<>();
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    args.put("approvedByHR", false);
    taskService.complete(task.getId(), args);
  }

  @Override
  public void approveLeaveApplicationByHr(final String processInstanceId) {
    final Map<String, Object> args = new HashMap<>();
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    args.put("approvedByHR", true);
    taskService.complete(task.getId(), args);
  }

  @Override
  public List<TaskDefinition> getProcessTasks(final String processInstanceId) {
    return taskService
        .createTaskQuery()
        .processInstanceId(processInstanceId)
        .list()
        .stream()
        .map(task -> new TaskDefinition(task.getId(), task.getName(), task.getProcessInstanceId()))
        .collect(Collectors.toList());
  }

  @Override
  public List<String> getActiveProcessesId() {
    return runtimeService
        .createProcessInstanceQuery()
        .active()
        .list()
        .stream()
        .map(process -> (process.getProcessInstanceId()))
        .collect(Collectors.toList());
  }
}
