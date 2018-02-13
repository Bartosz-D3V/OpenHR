package org.openhr.application.processes.leaveapplication;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.application.leaveapplication.enumeration.Role;
import org.openhr.application.leaveapplication.repository.LeaveApplicationRepository;
import org.openhr.application.leaveapplication.service.LeaveApplicationService;
import org.openhr.application.subject.service.SubjectService;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.address.Address;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class LeaveApplicationProcessTest {
  private final static Address mockAddress = new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL", "London",
    "UK");
  private final static PersonalInformation mockPersonalInformation = new PersonalInformation("John", null);
  private final static ContactInformation mockContactInformation = new ContactInformation("0123456789", "j.x@g.com",
    mockAddress);
  private final static EmployeeInformation mockEmployeeInformation = new EmployeeInformation("S8821 B", "Tester",
    "Core", "12A", null, null);
  private final static HrInformation mockHrInformation = new HrInformation(25L);
  private final static Subject mockSubject = new Subject("John", "Xavier", mockPersonalInformation,
    mockContactInformation, mockEmployeeInformation, mockHrInformation, new User("Jhn40", "testPass"));
  private final static LeaveApplication mockLeaveApplication = new LeaveApplication(LocalDate.now(), LocalDate.now().plusDays(5));
  private final static LeaveType leaveType = new LeaveType("Annual Leave", "Just a annual leave you've waited for!");

  @Autowired
  private LeaveApplicationService leaveApplicationService;

  @MockBean
  private SubjectService subjectService;

  @MockBean
  private LeaveApplicationRepository leaveApplicationRepository;

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private HistoryService historyService;

  @Autowired
  private SessionFactory sessionFactory;

  @Before
  public void setUp() {
    final Session session = sessionFactory.openSession();
    session.save(leaveType);
    session.close();
    mockLeaveApplication.setLeaveType(leaveType);
  }

  @Test
  public void processShouldStart() {
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
    when(subjectService.getLeftAllowanceInDays(anyLong())).thenReturn(25L);
    when(leaveApplicationRepository.dateRangeAlreadyBooked(anyLong(), anyObject(), anyObject())).thenReturn(false);

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

  @Test
  public void hrTeamShouldEndWorkflowByRejectingTheApplication() throws Exception {
    when(subjectService.getLeftAllowanceInDays(anyLong())).thenReturn(25L);
    when(leaveApplicationRepository.dateRangeAlreadyBooked(anyLong(), anyObject(), anyObject())).thenReturn(false);

    final LeaveApplication leaveApplication = leaveApplicationService
      .createLeaveApplication(mockSubject, mockLeaveApplication);
    final Map<String, Object> params = new HashMap<>();
    params.put("leaveApplication", leaveApplication);
    params.put("leaveApplicationId", leaveApplication.getApplicationId());
    params.put("approvedByManager", true);
    params.put("rejectedByManager", false);
    params.put("approvedByHR", false);
    params.put("rejectedByHR", true);
    params.put("role", Role.MANAGER);
    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave-application", params);
    final Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    taskService.complete(task.getId(), params);

    assertEquals("Manager reviews the application", task.getName());

    final List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
    assertEquals("HR reviews the application", tasks.get(0).getName());
    taskService.complete(tasks.get(0).getId());

    assertEquals(1, historyService.createHistoricProcessInstanceQuery().finished().count());
  }

  @Test
  public void hrTeamShouldEndWorkflowByApprovingTheApplication() throws ValidationException {
    when(subjectService.getLeftAllowanceInDays(anyLong())).thenReturn(25L);
    when(leaveApplicationRepository.dateRangeAlreadyBooked(anyLong(), anyObject(), anyObject())).thenReturn(false);

    final LeaveApplication leaveApplication = leaveApplicationService
      .createLeaveApplication(mockSubject, mockLeaveApplication);
    final Map<String, Object> params = new HashMap<>();
    params.put("leaveApplication", leaveApplication);
    params.put("leaveApplicationId", leaveApplication.getApplicationId());
    params.put("approvedByManager", true);
    params.put("rejectedByManager", false);
    params.put("approvedByHR", true);
    params.put("rejectedByHR", false);
    params.put("role", Role.MANAGER);
    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave-application", params);
    final Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    taskService.complete(task.getId(), params);

    assertEquals("Manager reviews the application", task.getName());

    final List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
    assertEquals("HR reviews the application", tasks.get(0).getName());
    taskService.complete(tasks.get(0).getId());

    assertEquals(1, historyService.createHistoricProcessInstanceQuery().finished().count());
  }
}
