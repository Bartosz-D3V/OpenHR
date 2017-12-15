package org.openhr.processes.leaveapplication;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.spring.boot.DataSourceProcessEngineAutoConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.BackendApplication;
import org.openhr.domain.address.Address;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.subject.ContactInformation;
import org.openhr.domain.subject.EmployeeInformation;
import org.openhr.domain.subject.PersonalInformation;
import org.openhr.domain.subject.Subject;
import org.openhr.enumeration.Role;
import org.openhr.processes.ContextResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;

@RunWith(SpringRunner.class)
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

  private RuntimeService runtimeService;

  @Before
  public void setUp() {
    final AnnotationConfigApplicationContext context = ContextResolver.getContext(
      DataSourceAutoConfiguration.class,
      DataSourceProcessEngineAutoConfiguration.DataSourceProcessEngineConfiguration.class);
    runtimeService = context.getBean(ProcessEngine.class).getRuntimeService();
  }

  @Test
  public void processShouldStart() throws Exception {
    final Map<String, Object> params = new HashMap<>();
    params.put("application", mockLeaveApplication);
    final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave-application", params);

    assertFalse(processInstance.isSuspended());
  }

}
