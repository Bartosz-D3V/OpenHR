package org.openhr.application.delegation.command;

import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.openhr.application.delegation.domain.DelegationApplication;
import org.openhr.common.domain.subject.Subject;
import org.springframework.stereotype.Service;

@Service
public class DelegationApplicationCommandImpl implements DelegationApplicationCommand {
  private final RuntimeService runtimeService;
  private final TaskService taskService;

  public DelegationApplicationCommandImpl(
      final RuntimeService runtimeService, final TaskService taskService) {
    this.runtimeService = runtimeService;
    this.taskService = taskService;
  }

  @Override
  public String startDelegationProcess(
      final Subject subject, final DelegationApplication delegationApplication) {
    final Map<String, Object> parameters = new HashMap<>();
    parameters.put("subject", subject);
    parameters.put("userId", subject.getUser().getUserId());
    parameters.put("emailAddress", subject.getContactInformation().getEmail());
    parameters.put("delegationApplication", delegationApplication);
    return runtimeService
        .startProcessInstanceByKey("delegation-application", parameters)
        .getProcessInstanceId();
  }

  @Override
  public void amendDelegationApplication(
      final String processInstanceId, final DelegationApplication delegationApplication) {
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    final Map<String, Object> parameters = new HashMap<>();
    parameters.put("delegationApplication", delegationApplication);
    taskService.complete(task.getId(), parameters);
  }

  @Override
  public void approveByManager(final String processInstanceId) {
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    final Map<String, Object> args = new HashMap<>();
    args.put("approvedByManager", true);
    taskService.complete(task.getId(), args);
  }

  @Override
  public void rejectByManager(final String processInstanceId) {
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    final Map<String, Object> args = new HashMap<>();
    args.put("approvedByManager", false);
    taskService.complete(task.getId(), args);
  }

  @Override
  public void approveByHr(final String processInstanceId) {
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    final Map<String, Object> args = new HashMap<>();
    args.put("approvedByHR", true);
    taskService.complete(task.getId(), args);
  }

  @Override
  public void rejectByHr(final String processInstanceId) {
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    final Map<String, Object> args = new HashMap<>();
    args.put("approvedByHR", false);
    taskService.complete(task.getId(), args);
  }
}
