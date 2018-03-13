package org.openhr.processes.delegationapplication;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
  private final static Employee mockSubject = new Employee(mockPersonalInformation,
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
    mockDelegationApplication.setSubject(mockSubject);
    session.save(mockDelegationApplication);
    session.flush();
    session.clear();
  }

  @After
  public void tearDown() {
    final Session session = sessionFactory.getCurrentSession();
    final String sql = "TRUNCATE TABLE DELEGATION_APPLICATION";
    final SQLQuery query = session.createSQLQuery(sql);
    query.executeUpdate();
  }

  @Test
  public void processShouldStart() {
    final Map<String, Object> params = new HashMap<>();
    params.put("subject", mockSubject);
    params.put("delegationApplication", mockDelegationApplication);
    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("delegation-application", params);

    assertFalse(processInstance.isSuspended());
  }
}
