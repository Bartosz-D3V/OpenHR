package org.openhr.application.delegationapplication.command;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.openhr.application.delegationapplication.domain.DelegationApplication;
import org.openhr.common.domain.subject.Subject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DelegationApplicationCommandImpl implements DelegationApplicationCommand {
  private final RuntimeService runtimeService;
  private final TaskService taskService;

  public DelegationApplicationCommandImpl(final RuntimeService runtimeService, final TaskService taskService) {
    this.runtimeService = runtimeService;
    this.taskService = taskService;
  }

  @Override
  public String startDelegationProcess(final Subject subject, final DelegationApplication delegationApplication) {
    final Map<String, Object> parameters = new HashMap<>();
    parameters.put("subject", subject);
    parameters.put("delegationApplication", delegationApplication);
    return runtimeService.startProcessInstanceByKey("delegation-application", parameters).getProcessInstanceId();
  }

  @Override
  public void assignToApplicant(final String processInstanceId, final DelegationApplication delegationApplication) {
    final Map<String, Object> parameters = new HashMap<>();
    final Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    parameters.put("delegationApplication", delegationApplication);
    taskService.complete(task.getId(), parameters);
  }

  @Override
  public void approveByManager(final String processInstanceId) {
    final Map<String, Object> args = new HashMap<>();
    final Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    args.put("approvedByManager", true);
    taskService.complete(task.getId(), args);
  }

  @Override
  public void rejectByManager(final String processInstanceId) {
    final Map<String, Object> args = new HashMap<>();
    final Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    args.put("approvedByManager", false);
    taskService.complete(task.getId(), args);
  }

  @Override
  public void approveByHr(final String processInstanceId) {
    final Map<String, Object> args = new HashMap<>();
    final Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    args.put("approvedByHR", true);
    taskService.complete(task.getId(), args);
  }

  @Override
  public void rejectByHr(final String processInstanceId) {
    final Map<String, Object> args = new HashMap<>();
    final Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    args.put("approvedByHR", false);
    taskService.complete(task.getId(), args);
  }
}
