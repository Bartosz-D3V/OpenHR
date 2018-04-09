package org.openhr.processes.delegationapplication;

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
import org.openhr.application.delegation.domain.DelegationApplication;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional(propagation = Propagation.NEVER)
public class DelegationApplicationProcessTest {
  private final Employee mockEmployee = new Employee(
    new PersonalInformation("John", "Xavier", "Alex", null),
    new ContactInformation(), new EmployeeInformation(), new HrInformation(),
    new User(UUID.randomUUID().toString().substring(0, 19), ""));
  private final Manager mockManager = new Manager(
    new PersonalInformation("John", "Xavier", "Alex", null),
    new ContactInformation(), new EmployeeInformation(), new HrInformation(),
    new User(UUID.randomUUID().toString().substring(0, 19), ""));
  private final HrTeamMember mockHrTeamMember = new HrTeamMember(
    new PersonalInformation("John", "Xavier", "Alex", null),
    new ContactInformation(), new EmployeeInformation(), new HrInformation(),
    new User(UUID.randomUUID().toString().substring(0, 19), ""));
  private final DelegationApplication mockDelegationApplication = new DelegationApplication(LocalDate.now(),
    LocalDate.now().plusDays(10));

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
  }

  @Test
  public void processShouldStart() {
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockEmployee);
    params.put("delegationApplication", mockDelegationApplication);
    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("delegation-application", params);

    assertFalse(processInstance.isSuspended());
  }

  @Test
  public void managerReviewsApplicationShouldBeTheFirstStepForEmployee() {
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockEmployee);
    params.put("delegationApplication", mockDelegationApplication);

    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("delegation-application", params);
    final Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

    final Session session = sessionFactory.getCurrentSession();
    final DelegationApplication delegationApplication =
      session.get(DelegationApplication.class, mockDelegationApplication.getApplicationId());

    assertEquals("Manager reviews application", task.getName());
    assertEquals(processInstance.getProcessInstanceId(), delegationApplication.getProcessInstanceId());
  }

  @Test
  public void afterApplicationIsBeingRejectedByManagerItShouldBeMarkedAsRejectedAndAssignedBack() {
    Map<String, Object> params = new HashMap<>();
    params.put("subject", mockEmployee);
    params.put("delegationApplication", mockDelegationApplication);

    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("delegation-application", params);
    final Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    params = new HashMap<>();
    params.put("approvedByManager", false);
    params.put("delegationApplication", mockDelegationApplication);
    taskService.complete(task.getId(), params);
    final Task task2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

    final Session session = sessionFactory.getCurrentSession();
    final DelegationApplication actualDelegationApplication = session.get(DelegationApplication.class,
      mockDelegationApplication.getApplicationId());
    final Employee actualEmployee = session.get(Employee.class, mockEmployee.getSubjectId());

    assertEquals("Amend the application", task2.getName());
    assertFalse(actualDelegationApplication.isApprovedByManager());
    assertEquals(actualEmployee, actualDelegationApplication.getAssignee());
  }

  @Test
  public void afterApplicationIsBeingAcceptedByManagerItShouldBeAssignedToHR() {
    Map<String, Object> params = new HashMap<>();
    params.put("subject", mockEmployee);
    params.put("delegationApplication", mockDelegationApplication);

    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("delegation-application", params);
    final Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    params = new HashMap<>();
    params.put("approvedByManager", true);
    params.put("delegationApplication", mockDelegationApplication);
    taskService.complete(task.getId(), params);
    final Task task2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

    final Session session = sessionFactory.getCurrentSession();
    final DelegationApplication delegationApplication = session.get(DelegationApplication.class,
      mockDelegationApplication.getApplicationId());
    final HrTeamMember hrTeamMember = session.get(HrTeamMember.class, mockHrTeamMember.getSubjectId());

    assertEquals("HR reviews application", task2.getName());
    assertTrue(delegationApplication.isApprovedByManager());
    assertEquals(delegationApplication.getAssignee(), hrTeamMember);
  }

  @Test
  public void afterApplicationIsBeingAcceptedByHRItShouldBeTerminated() {
    Map<String, Object> params = new HashMap<>();
    params.put("subject", mockEmployee);
    params.put("delegationApplication", mockDelegationApplication);

    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("delegation-application", params);
    final Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    params = new HashMap<>();
    params.put("approvedByManager", true);
    params.put("delegationApplication", mockDelegationApplication);
    taskService.complete(task.getId(), params);

    params = new HashMap<>();
    params.put("approvedByHR", true);
    params.put("delegationApplication", mockDelegationApplication);
    final Task task2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    taskService.complete(task2.getId(), params);

    final Session session = sessionFactory.getCurrentSession();
    final DelegationApplication delegationApplication = session.get(DelegationApplication.class,
      mockDelegationApplication.getApplicationId());

    assertTrue(delegationApplication.isApprovedByManager());
    assertTrue(delegationApplication.isApprovedByHR());
    assertTrue(delegationApplication.isTerminated());
  }

  @Test
  public void afterApplicationIsBeingRejectedByHRItShouldBeAssignedBackToApplicant() {
    Map<String, Object> params = new HashMap<>();
    params.put("subject", mockEmployee);
    params.put("delegationApplication", mockDelegationApplication);

    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("delegation-application", params);
    final Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    params = new HashMap<>();
    params.put("approvedByManager", true);
    params.put("delegationApplication", mockDelegationApplication);
    taskService.complete(task.getId(), params);

    params = new HashMap<>();
    params.put("approvedByHR", false);
    params.put("delegationApplication", mockDelegationApplication);
    final Task task2 = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    taskService.complete(task2.getId(), params);

    final Session session = sessionFactory.getCurrentSession();
    final DelegationApplication delegationApplication = session.get(DelegationApplication.class,
      mockDelegationApplication.getApplicationId());
    final Employee employee = session.get(Employee.class, mockEmployee.getSubjectId());

    assertFalse(delegationApplication.isApprovedByHR());
    assertEquals(employee, delegationApplication.getAssignee());
  }

  @Test
  public void hrReviewsApplicationShouldBeTheFirstStepForManager() {
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockManager);
    params.put("delegationApplication", mockDelegationApplication);

    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("delegation-application", params);
    final Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

    assertEquals("HR reviews application", task.getName());
  }

  @Test
  public void processShouldEndImmediatelyIfHRTeamMemberIsApplicant() {
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockHrTeamMember);
    params.put("delegationApplication", mockDelegationApplication);

    runtimeService.startProcessInstanceByKey("delegation-application", params);

    final Session session = sessionFactory.getCurrentSession();
    final DelegationApplication delegationApplication =
      session.get(DelegationApplication.class, mockDelegationApplication.getApplicationId());

    assertTrue(delegationApplication.isApprovedByHR());
    assertTrue(delegationApplication.isTerminated());
  }
}
