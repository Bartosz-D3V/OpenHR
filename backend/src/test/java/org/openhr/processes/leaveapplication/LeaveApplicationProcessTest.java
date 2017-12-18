package org.openhr.processes.leaveapplication;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.domain.address.Address;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.subject.ContactInformation;
import org.openhr.domain.subject.EmployeeInformation;
import org.openhr.domain.subject.PersonalInformation;
import org.openhr.domain.subject.Subject;
import org.openhr.enumeration.Role;
import org.openhr.service.leaveapplication.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@WebAppConfiguration
public class LeaveApplicationProcessTest {
  private final static Address mockAddress = new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL", "London",
    "UK");
  private final static PersonalInformation mockPersonalInformation = new PersonalInformation("John", null);
  private final static ContactInformation mockContactInformation = new ContactInformation("0123456789", "j.x@g.com",
    mockAddress);
  private final static EmployeeInformation mockEmployeeInformation = new EmployeeInformation("S8821 B", "Tester",
    "12A", null, null);
  private final static Subject mockSubject = new Subject("John", "Xavier", Role.EMPLOYEE, mockPersonalInformation,
    mockContactInformation, mockEmployeeInformation);

  private final static LeaveApplication mockLeaveApplication = new LeaveApplication(null, null);

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private HistoryService historyService;

  @Autowired
  private LeaveApplicationService leaveApplicationService;

  @Test
  public void processShouldStart() throws Exception {
    final Map<String, Object> params = new HashMap<>();
    params.put("application", mockLeaveApplication);
    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave-application", params);

    assertFalse(processInstance.isSuspended());
  }

  @Test
  public void reviewShouldBeTheFirstStep() throws Exception {
    final Map<String, Object> params = new HashMap<>();
    params.put("application", mockLeaveApplication);
    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave-application", params);
    final Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

    assertEquals("Manager reviews the application", task.getName());
  }

  @Test
  public void managerShouldEndWorkflowByRejectingTheApplication() throws Exception {
    final LeaveApplication leaveApplication = leaveApplicationService
      .createLeaveApplication(mockSubject, mockLeaveApplication);
    final Map<String, Object> params = new HashMap<>();
    params.put("leaveApplication", leaveApplication);
    params.put("leaveApplicationId", leaveApplication.getApplicationId());
    params.put("rejectedByManager", true);
    params.put("approvedByManager", false);
    params.put("role", Role.MANAGER);
    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave-application", params);
    final Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    taskService.complete(task.getId(), params);
    final List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
    final LeaveApplication updatedLeaveApplication = leaveApplicationService
      .getLeaveApplication(leaveApplication.getApplicationId());

    assertEquals(0, tasks.size());
    assertEquals(1, historyService.createHistoricProcessInstanceQuery().finished().count());
    assertFalse(updatedLeaveApplication.isApprovedByManager());
  }

}
