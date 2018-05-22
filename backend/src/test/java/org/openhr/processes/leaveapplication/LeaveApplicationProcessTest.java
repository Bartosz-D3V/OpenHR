package org.openhr.processes.leaveapplication;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.allowance.service.AllowanceService;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.application.leaveapplication.repository.LeaveApplicationRepository;
import org.openhr.application.leaveapplication.service.LeaveApplicationService;
import org.openhr.application.subject.service.SubjectService;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.service.UserService;
import org.openhr.common.domain.address.Address;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.enumeration.Role;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.subethamail.wiser.Wiser;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class LeaveApplicationProcessTest {
  private final Address mockAddress =
      new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL", "London", "UK");
  private final PersonalInformation mockPersonalInformation =
      new PersonalInformation("John", "Xavier", "Alex", null);
  private final ContactInformation mockContactInformation =
      new ContactInformation("0123456789", "j.x@g.com", mockAddress);
  private final EmployeeInformation mockEmployeeInformation =
      new EmployeeInformation("S8821 B", "Tester", "Core", "12A", null, null);
  private final HrInformation mockHrInformation = new HrInformation(25L);
  private final Employee mockSubject =
      new Employee(
          mockPersonalInformation,
          mockContactInformation,
          mockEmployeeInformation,
          mockHrInformation,
          new User(UUID.randomUUID().toString().substring(0, 19), "testPass"));
  private final LeaveApplication mockLeaveApplication =
      new LeaveApplication(LocalDate.now(), LocalDate.now().plusDays(5));
  private final LeaveType leaveType =
      new LeaveType("Annual Leave", "Just a annual leave you've waited for!");

  @Autowired private LeaveApplicationService leaveApplicationService;

  @MockBean private SubjectService subjectService;

  @MockBean private AllowanceService allowanceService;

  @MockBean private UserService userService;

  @SpyBean private LeaveApplicationRepository leaveApplicationRepository;

  @Autowired private RuntimeService runtimeService;

  @Autowired private TaskService taskService;

  @Autowired private SessionFactory sessionFactory;

  private final Wiser wiser = new Wiser();

  @Before
  public void setUp() {
    final Session session = sessionFactory.getCurrentSession();
    session.save(leaveType);
    session.save(mockSubject);
    session.flush();
    session.clear();
    mockLeaveApplication.setLeaveType(leaveType);
    mockLeaveApplication.setSubject(mockSubject);

    wiser.setHostname("localhost");
    wiser.setPort(1025);
    wiser.start();
  }

  @After
  public void tearDown() {
    wiser.stop();
    final Session session = sessionFactory.getCurrentSession();
    final String sql = "TRUNCATE TABLE LEAVE_APPLICATION";
    final SQLQuery query = session.createSQLQuery(sql);
    query.executeUpdate();
  }

  @Test
  public void processShouldStart() {
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockSubject);
    params.put("leaveApplication", mockLeaveApplication);
    params.put("applicationId", mockLeaveApplication);
    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("leave-application", params);

    assertFalse(processInstance.isSuspended());
  }

  @Test
  public void processShouldStartWithoutRoleUsingDefaultRoute() {
    when(leaveApplicationService.getLeaveTypeById(leaveType.getLeaveTypeId()))
        .thenReturn(leaveType);
    final Map<String, Object> params = new HashMap<>();

    final Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(mockSubject);
    session.save(mockLeaveApplication);
    session.flush();
    session.clear();

    mockSubject.setRole(null);
    params.put("subject", mockSubject);
    params.put("leaveApplication", mockLeaveApplication);
    params.put("applicationId", mockLeaveApplication.getApplicationId());
    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("leave-application", params);

    assertFalse(processInstance.isSuspended());
  }

  @Test
  public void reviewShouldBeTheFirstStep() {
    when(leaveApplicationService.getLeaveTypeById(leaveType.getLeaveTypeId()))
        .thenReturn(leaveType);

    final Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(mockSubject);
    session.flush();
    session.clear();

    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockSubject);
    params.put("leaveApplication", mockLeaveApplication);
    params.put("applicationId", mockLeaveApplication.getApplicationId());
    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("leave-application", params);
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

    assertEquals("Manager reviews the application", task.getName());
  }

  @Test
  public void managerShouldEndWorkflowByRejectingTheApplication() throws Exception {
    when(leaveApplicationService.getLeaveTypeById(leaveType.getLeaveTypeId()))
        .thenReturn(leaveType);
    when(allowanceService.getLeftAllowanceInDays(anyLong())).thenReturn(25L);
    when(leaveApplicationRepository.dateRangeAlreadyBooked(anyLong(), anyObject(), anyObject()))
        .thenReturn(false);

    final Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(mockSubject);
    session.flush();
    session.clear();

    final LeaveApplication leaveApplication =
        leaveApplicationService.createLeaveApplication(mockSubject, mockLeaveApplication);
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockSubject);
    params.put("emailAddress", mockSubject.getContactInformation().getEmail());
    params.put("leaveApplication", leaveApplication);
    params.put("applicationId", leaveApplication.getApplicationId());
    params.put("approvedByManager", false);
    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("leave-application", params);
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    taskService.complete(task.getId(), params);
    final List<Task> tasks =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
    final LeaveApplication updatedLeaveApplication =
        leaveApplicationService.getLeaveApplication(leaveApplication.getApplicationId());

    assertEquals(0, tasks.size());
    assertEquals(1, wiser.getMessages().size());
    assertTrue(updatedLeaveApplication.isTerminated());
    assertFalse(updatedLeaveApplication.isApprovedByManager());
  }

  @Test
  public void hrTeamShouldEndWorkflowByRejectingTheApplication() throws Exception {
    when(userService.notificationsEnabled(mockSubject.getUser().getUserId())).thenReturn(true);
    when(leaveApplicationService.getLeaveTypeById(leaveType.getLeaveTypeId()))
        .thenReturn(leaveType);
    when(allowanceService.getLeftAllowanceInDays(anyLong())).thenReturn(25L);
    when(leaveApplicationRepository.dateRangeAlreadyBooked(anyLong(), anyObject(), anyObject()))
        .thenReturn(false);
    when(subjectService.getSubjectDetails(anyLong())).thenReturn(mockSubject);

    final Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(mockSubject);
    session.flush();
    session.clear();

    final LeaveApplication leaveApplication =
        leaveApplicationService.createLeaveApplication(mockSubject, mockLeaveApplication);
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockSubject);
    params.put("userId", mockSubject.getUser().getUserId());
    params.put("emailAddress", mockSubject.getContactInformation().getEmail());
    params.put("leaveApplication", leaveApplication);
    params.put("applicationId", leaveApplication.getApplicationId());
    params.put("approvedByManager", true);
    params.put("approvedByHR", false);
    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("leave-application", params);
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    taskService.complete(task.getId(), params);

    assertEquals("Manager reviews the application", task.getName());

    final List<Task> tasks =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
    assertEquals("HR reviews the application", tasks.get(0).getName());
    taskService.complete(tasks.get(0).getId(), params);

    final LeaveApplication updatedLeaveApplication =
        session.get(LeaveApplication.class, leaveApplication.getApplicationId());

    assertTrue(updatedLeaveApplication.isTerminated());
    assertEquals(1, wiser.getMessages().size());
  }

  @Test
  public void hrTeamShouldEndWorkflowByApprovingTheApplication()
      throws ValidationException, SubjectDoesNotExistException {
    when(userService.notificationsEnabled(mockSubject.getUser().getUserId())).thenReturn(true);
    when(leaveApplicationService.getLeaveTypeById(leaveType.getLeaveTypeId()))
        .thenReturn(leaveType);
    when(allowanceService.getLeftAllowanceInDays(anyLong())).thenReturn(25L);
    when(leaveApplicationRepository.dateRangeAlreadyBooked(anyLong(), anyObject(), anyObject()))
        .thenReturn(false);
    when(subjectService.getSubjectDetails(anyLong())).thenReturn(mockSubject);

    final Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(mockSubject);
    session.flush();
    session.clear();

    final LeaveApplication mockLeaveApplication =
        new LeaveApplication(LocalDate.now(), LocalDate.now().plusDays(5));
    mockLeaveApplication.setLeaveType(leaveType);

    final LeaveApplication leaveApplication =
        leaveApplicationService.createLeaveApplication(mockSubject, mockLeaveApplication);
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockSubject);
    params.put("userId", mockSubject.getUser().getUserId());
    params.put("emailAddress", mockSubject.getContactInformation().getEmail());
    params.put("leaveApplication", leaveApplication);
    params.put("applicationId", leaveApplication.getApplicationId());
    params.put("approvedByManager", true);
    params.put("approvedByHR", true);
    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("leave-application", params);
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
    taskService.complete(task.getId(), params);

    assertEquals("Manager reviews the application", task.getName());

    final List<Task> tasks =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
    assertEquals("HR reviews the application", tasks.get(0).getName());
    taskService.complete(tasks.get(0).getId());

    final LeaveApplication updatedLeaveApplication =
        session.get(LeaveApplication.class, leaveApplication.getApplicationId());

    assertTrue(updatedLeaveApplication.isTerminated());
    assertEquals(1, wiser.getMessages().size());
  }

  @Test
  public void processShouldStartAndSetApprovedByManagerIfManagerMadeAnApplication()
      throws ValidationException, SubjectDoesNotExistException {
    when(leaveApplicationService.getLeaveTypeById(leaveType.getLeaveTypeId()))
        .thenReturn(leaveType);
    when(allowanceService.getLeftAllowanceInDays(anyLong())).thenReturn(25L);
    when(leaveApplicationRepository.dateRangeAlreadyBooked(anyLong(), anyObject(), anyObject()))
        .thenReturn(false);
    when(subjectService.getSubjectDetails(anyLong())).thenReturn(mockSubject);

    final Session session = sessionFactory.getCurrentSession();
    mockSubject.setRole(Role.MANAGER);
    session.saveOrUpdate(mockSubject);
    session.flush();
    session.clear();

    final LeaveApplication leaveApplication =
        leaveApplicationService.createLeaveApplication(mockSubject, mockLeaveApplication);
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockSubject);
    params.put("applicationId", leaveApplication.getApplicationId());
    final ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey("leave-application", params);
    final Task task =
        taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

    assertEquals("HR reviews the application", task.getName());
  }

  @Test
  public void workflowShouldEndIfHrTeamMemberAppliedForLeave()
      throws ValidationException, SubjectDoesNotExistException {
    when(userService.notificationsEnabled(mockSubject.getUser().getUserId())).thenReturn(true);
    when(leaveApplicationService.getLeaveTypeById(leaveType.getLeaveTypeId()))
        .thenReturn(leaveType);
    when(allowanceService.getLeftAllowanceInDays(anyLong())).thenReturn(25L);
    when(leaveApplicationRepository.dateRangeAlreadyBooked(anyLong(), anyObject(), anyObject()))
        .thenReturn(false);
    when(subjectService.getSubjectDetails(anyLong())).thenReturn(mockSubject);

    final Session session = sessionFactory.getCurrentSession();
    mockSubject.setRole(Role.HRTEAMMEMBER);
    session.saveOrUpdate(mockSubject);
    session.flush();
    session.clear();

    final LeaveApplication leaveApplication =
        leaveApplicationService.createLeaveApplication(mockSubject, mockLeaveApplication);
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockSubject);
    params.put("userId", mockSubject.getUser().getUserId());
    params.put("emailAddress", mockSubject.getContactInformation().getEmail());
    params.put("applicationId", leaveApplication.getApplicationId());
    runtimeService.startProcessInstanceByKey("leave-application", params);

    final LeaveApplication updatedLeaveApplication =
        session.get(LeaveApplication.class, leaveApplication.getApplicationId());

    assertTrue(updatedLeaveApplication.isTerminated());
    assertEquals(1, wiser.getMessages().size());
  }
}
