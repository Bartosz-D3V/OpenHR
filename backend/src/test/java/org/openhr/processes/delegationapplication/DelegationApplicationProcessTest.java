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
import org.openhr.application.delegationapplication.domain.DelegationApplication;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.address.Address;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class DelegationApplicationProcessTest {
  private final static Address mockAddress = new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL", "London",
    "UK");
  private final static PersonalInformation mockPersonalInformation = new PersonalInformation("John", "Xavier", "Alex", null);
  private final static ContactInformation mockContactInformation = new ContactInformation("0123456789", "j.x@g.com",
    mockAddress);
  private final static EmployeeInformation mockEmployeeInformation = new EmployeeInformation("S8821 B", "Tester",
    "Core", "12A", null, null);
  private final static HrInformation mockHrInformation = new HrInformation(25L);
  private final static Employee mockEmployee = new Employee(mockPersonalInformation,
    mockContactInformation, mockEmployeeInformation, mockHrInformation, new User("Jhn40", "testPass"));
  private final static DelegationApplication mockDelegationApplication = new DelegationApplication(LocalDate.now(),
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
    mockEmployee.setRole(Role.EMPLOYEE);
    mockDelegationApplication.setSubject(mockEmployee);
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

    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("delegation-application", params);
    final Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

    assertEquals("Manager reviews application", task.getName());
  }

  @Test
  public void afterApplicationIsBeingRejectedByManagerItShouldBeMarkedAsRejectedAndAssignedBack() {
    Map<String, Object> params = new HashMap<>();
    params.put("subject", mockEmployee);

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
}
