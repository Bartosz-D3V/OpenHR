package org.openhr.application.leaveapplication.command;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.common.domain.process.TaskDefinition;
import org.openhr.application.leaveapplication.enumeration.Role;
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
  public String startLeaveApplicationProcess(final LeaveApplication leaveApplication) {
    final Map<String, Object> parameters = new HashMap<>();
    parameters.put("leaveApplication", leaveApplication);
    return runtimeService.startProcessInstanceByKey("leave-application", parameters).getProcessInstanceId();
  }

  @Override
  public void rejectLeaveApplication(final Role role, final String processInstanceId) {
    final Map<String, Object> args = new HashMap<>();
    final Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    args.put("role", role);
    args.put("rejectedByManager", true);
    args.put("approvedByManager", false);
    taskService.complete(task.getId(), args);
  }

  @Override
  public void approveLeaveApplication(final Role role, final String processInstanceId) {
    final Map<String, Object> args = new HashMap<>();
    final Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    args.put("role", role);
    args.put("rejectedByManager", false);
    args.put("approvedByManager", true);
    taskService.complete(task.getId(), args);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<TaskDefinition> getProcessTasks(final String processInstanceId) {
    return taskService.createTaskQuery()
      .processInstanceId(processInstanceId)
      .list()
      .stream()
      .map(task -> new TaskDefinition(task.getId(), task.getName(), task.getProcessInstanceId()))
      .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
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