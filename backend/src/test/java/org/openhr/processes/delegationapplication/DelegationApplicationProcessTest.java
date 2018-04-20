package org.openhr.processes.delegationapplication;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.delegation.domain.DelegationApplication;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.service.UserService;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.subethamail.wiser.Wiser;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional(propagation = Propagation.NEVER)
public class DelegationApplicationProcessTest {
  private final Employee mockEmployee =
      new Employee(
          new PersonalInformation("John", "Xavier", "Alex", null),
          new ContactInformation("", "j.x@mail.com", null),
          new EmployeeInformation(),
          new HrInformation(),
          new User(UUID.randomUUID().toString().substring(0, 19), ""));
  private final Manager mockManager =
      new Manager(
          new PersonalInformation("John", "Xavier", "Alex", null),
          new ContactInformation("", "j.x@mail.com", null),
          new EmployeeInformation(),
          new HrInformation(),
          new User(UUID.randomUUID().toString().substring(0, 19), ""));
  private final HrTeamMember mockHrTeamMember =
      new HrTeamMember(
          new PersonalInformation("John", "Xavier", "Alex", null),
          new ContactInformation("", "j.x@mail.com", null),
          new EmployeeInformation(),
          new HrInformation(),
          new User(UUID.randomUUID().toString().substring(0, 19), ""));
  private final DelegationApplication mockDelegationApplication =
      new DelegationApplication(LocalDate.now(), LocalDate.now().plusDays(10));

  @MockBean private UserService userService;

  @Autowired private RuntimeService runtimeService;

  @Autowired private TaskService taskService;

  @Autowired private SessionFactory sessionFactory;

  private final Wiser wiser = new Wiser();

  @Before
  public void setUp() {
    final Session session = sessionFactory.getCurrentSession();
    mockHrTeamMember.setRole(Role.HRTEAMMEMBER);
    mockManager.setRole(Role.MANAGER);
    mockEmployee.setRole(Role.EMPLOYEE);
    mockEmployee.setManager(mockManager);
    mockManager.setHrTeamMember(mockHrTeamMember);
    mockDelegationApplication.setSubject(mockEmployee);
    session.save(mockHrTeamMember);
    session.save(mockManager);
    session.save(mockEmployee);
    session.save(mockDelegationApplication);
    session.flush();
    session.clear();

    wiser.setHostname("localhost");
    wiser.setPort(1025);
    wiser.start();
  }

  @After
  public void tearDown() {
    wiser.stop();
  }

  @Test
  public void processShouldStart() {
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockEmployee);
    params.put("delegationApplication", mockDelegationApplication);
    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("delegation-application", params);

    assertFalse(processInstance.isSuspended());
  }

  @Test
  public void managerReviewsApplicationShouldBeTheFirstStepForEmployee() {
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockEmployee);
    params.put("delegationApplication", mockDelegationApplication);

    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("delegation-application", params);
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

    final Session session = sessionFactory.getCurrentSession();
    final DelegationApplication delegationApplication =
        session.get(DelegationApplication.class, mockDelegationApplication.getApplicationId());

    assertEquals("Manager reviews application", task.getName());
    assertEquals(
        processInstance.getProcessInstanceId(), delegationApplication.getProcessInstanceId());
  }

  @Test
  public void afterApplicationIsBeingRejectedByManagerItShouldBeMarkedAsRejectedAndAssignedBack() {
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockEmployee);
    params.put("delegationApplication", mockDelegationApplication);
    params.put("approvedByManager", false);

    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("delegation-application", params);
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    taskService.complete(task.getId(), params);
    final Task task2 =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

    final Session session = sessionFactory.getCurrentSession();
    final DelegationApplication actualDelegationApplication =
        session.get(DelegationApplication.class, mockDelegationApplication.getApplicationId());
    final Employee actualEmployee = session.get(Employee.class, mockEmployee.getSubjectId());

    assertEquals("Amend the application", task2.getName());
    assertFalse(actualDelegationApplication.isApprovedByManager());
    assertEquals(actualEmployee, actualDelegationApplication.getAssignee());
  }

  @Test
  public void afterAssigningBackApplicantShouldUpdateApplication() {
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockEmployee);
    params.put("delegationApplication", mockDelegationApplication);
    params.put("approvedByManager", false);
    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("delegation-application", params);
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    taskService.complete(task.getId(), params);

    mockDelegationApplication.setBudget(new BigDecimal(1200));
    final Task task2 =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    final Map<String, Object> param = new HashMap<>();
    param.put("delegationApplication", mockDelegationApplication);
    taskService.complete(task2.getId(), param);
    final Task task3 =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

    assertEquals("Manager reviews application", task3.getName());
  }

  @Test
  public void afterApplicationIsBeingAcceptedByManagerItShouldBeAssignedToHR() {
    Map<String, Object> params = new HashMap<>();
    params.put("subject", mockEmployee);
    params.put("delegationApplication", mockDelegationApplication);

    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("delegation-application", params);
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    params = new HashMap<>();
    params.put("approvedByManager", true);
    params.put("delegationApplication", mockDelegationApplication);
    taskService.complete(task.getId(), params);
    final Task task2 =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

    final Session session = sessionFactory.getCurrentSession();
    final DelegationApplication delegationApplication =
        session.get(DelegationApplication.class, mockDelegationApplication.getApplicationId());
    final HrTeamMember hrTeamMember =
        session.get(HrTeamMember.class, mockHrTeamMember.getSubjectId());

    assertEquals("HR reviews application", task2.getName());
    assertTrue(delegationApplication.isApprovedByManager());
    assertEquals(delegationApplication.getAssignee(), hrTeamMember);
  }

  @Test
  public void afterApplicationIsBeingAcceptedByHRItShouldBeTerminated() {
    when(userService.notificationsEnabled(mockEmployee.getUser().getUserId())).thenReturn(true);
    Map<String, Object> params = new HashMap<>();
    params.put("subject", mockEmployee);
    params.put("userId", mockEmployee.getUser().getUserId());
    params.put("emailAddress", mockEmployee.getContactInformation().getEmail());
    params.put("delegationApplication", mockDelegationApplication);

    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("delegation-application", params);
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    params = new HashMap<>();
    params.put("approvedByManager", true);
    params.put("delegationApplication", mockDelegationApplication);
    taskService.complete(task.getId(), params);

    params = new HashMap<>();
    params.put("approvedByHR", true);
    params.put("delegationApplication", mockDelegationApplication);
    final Task task2 =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    taskService.complete(task2.getId(), params);

    final Session session = sessionFactory.getCurrentSession();
    final DelegationApplication delegationApplication =
        session.get(DelegationApplication.class, mockDelegationApplication.getApplicationId());

    assertTrue(delegationApplication.isApprovedByManager());
    assertTrue(delegationApplication.isApprovedByHR());
    assertTrue(delegationApplication.isTerminated());
    assertEquals(1, wiser.getMessages().size());
  }

  @Test
  public void afterApplicationIsBeingRejectedByHRItShouldBeAssignedBackToApplicant() {
    Map<String, Object> params = new HashMap<>();
    params.put("subject", mockEmployee);
    params.put("delegationApplication", mockDelegationApplication);

    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("delegation-application", params);
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    params = new HashMap<>();
    params.put("approvedByManager", true);
    params.put("delegationApplication", mockDelegationApplication);
    taskService.complete(task.getId(), params);

    params = new HashMap<>();
    params.put("approvedByHR", false);
    params.put("delegationApplication", mockDelegationApplication);
    final Task task2 =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    taskService.complete(task2.getId(), params);

    final Session session = sessionFactory.getCurrentSession();
    final DelegationApplication delegationApplication =
        session.get(DelegationApplication.class, mockDelegationApplication.getApplicationId());
    final Employee employee = session.get(Employee.class, mockEmployee.getSubjectId());

    assertFalse(delegationApplication.isApprovedByHR());
    assertEquals(employee, delegationApplication.getAssignee());
  }

  @Test
  public void hrReviewsApplicationShouldBeTheFirstStepForManager() {
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockManager);
    params.put("delegationApplication", mockDelegationApplication);

    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("delegation-application", params);
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

    assertEquals("HR reviews application", task.getName());
  }

  @Test
  public void processShouldEndImmediatelyIfHRTeamMemberIsApplicant() {
    when(userService.notificationsEnabled(mockEmployee.getUser().getUserId())).thenReturn(true);
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockHrTeamMember);
    params.put("userId", mockEmployee.getUser().getUserId());
    params.put("emailAddress", mockEmployee.getContactInformation().getEmail());
    params.put("delegationApplication", mockDelegationApplication);

    runtimeService.startProcessInstanceByKey("delegation-application", params);

    final Session session = sessionFactory.getCurrentSession();
    final DelegationApplication delegationApplication =
        session.get(DelegationApplication.class, mockDelegationApplication.getApplicationId());

    assertTrue(delegationApplication.isApprovedByHR());
    assertTrue(delegationApplication.isTerminated());
    assertEquals(1, wiser.getMessages().size());
  }
}
