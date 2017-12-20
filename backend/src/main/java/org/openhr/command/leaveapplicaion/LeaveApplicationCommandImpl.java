package org.openhr.command.leaveapplicaion;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.process.TaskDefinition;
import org.openhr.enumeration.Role;
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
  public void startLeaveApplicationProcess(final LeaveApplication leaveApplication) {
    final Map<String, Object> parameters = new HashMap<>();
    parameters.put("leaveApplication", leaveApplication);
    runtimeService.startProcessInstanceByKey("leave-application", parameters);
  }

  @Override
  public void rejectLeaveApplication(final Role role, final String taskId) {
    final Map<String, Object> args = new HashMap<>();
    final Task task = taskService.createTaskQuery().processInstanceId(taskId).singleResult();
    args.put("role", role);
    args.put("rejectedByManager", true);
    taskService.complete(task.getId(), args);
  }

  @Override
  public void approveLeaveApplication(Role role, String taskId) {
    final Map<String, Object> args = new HashMap<>();
    final Task task = taskService.createTaskQuery().processInstanceId(taskId).singleResult();
    args.put("role", role);
    args.put("approvedByManager", true);
    taskService.complete(task.getId(), args);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public final List<TaskDefinition> getProcessTasks(final String processInstanceId) {
    return taskService.createTaskQuery()
      .processInstanceBusinessKey(processInstanceId)
      .list()
      .stream()
      .map(task -> new TaskDefinition(task.getId(), task.getName(), task.getProcessInstanceId()))
      .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public final List<Long> getActiveProcessesId() {
    return runtimeService
      .createProcessInstanceQuery()
      .active()
      .list()
      .stream()
      .map(process -> new Long(process.getDeploymentId()))
      .collect(Collectors.toList());
  }
}
