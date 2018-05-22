package org.openhr.application.leaveapplication.dao;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.address.Address;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LeaveApplicationDAOTest {
  private final Address mockAddress =
      new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL", "London", "UK");
  private final PersonalInformation mockPersonalInformation =
      new PersonalInformation("John", "Alex", null, null);
  private final ContactInformation mockContactInformation =
      new ContactInformation("0123456789", "j.x@g.com", mockAddress);
  private final EmployeeInformation mockEmployeeInformation =
      new EmployeeInformation("S8821 B", "Tester", "Core", "12A", null, null);
  private final HrInformation mockHrInformation = new HrInformation(25);
  private final Employee mockEmployee =
      new Employee(
          mockPersonalInformation,
          mockContactInformation,
          mockEmployeeInformation,
          mockHrInformation,
          new User("Mck40", "testPass"));
  private final Manager mockManager =
      new Manager(
          mockPersonalInformation,
          mockContactInformation,
          mockEmployeeInformation,
          mockHrInformation,
          new User("Mck42", "testPass"));
  private final LeaveApplication mockLeaveApplication =
      new LeaveApplication(LocalDate.now(), LocalDate.now().plusDays(5));
  private final LeaveType leaveType =
      new LeaveType("Annual Leave", "Just a annual leave you've waited for!");

  @Autowired private SessionFactory sessionFactory;

  @Autowired private LeaveApplicationDAO leaveApplicationDAO;

  @Before
  public void setUp() {
    final Session session = sessionFactory.getCurrentSession();
    mockLeaveApplication.setProcessInstanceId(String.valueOf(5L));
    mockLeaveApplication.setApprovedByManager(true);
    mockLeaveApplication.setApprovedByHR(false);
    mockLeaveApplication.setTerminated(false);
    mockLeaveApplication.setSubject(mockEmployee);
    mockLeaveApplication.setAssignee(mockManager);
    mockLeaveApplication.setMessage("I am going to Vanuatu!");
    mockLeaveApplication.setLeaveType(leaveType);
    session.save(leaveType);
    session.save(mockEmployee);
    session.save(mockManager);
  }

  @Test
  public void getLeaveApplicationShouldReturnApplication() throws ApplicationDoesNotExistException {
    final Session session = sessionFactory.getCurrentSession();
    mockLeaveApplication.setSubject(mockEmployee);
    session.save(mockLeaveApplication);
    final LeaveApplication actualLeaveApplication =
        leaveApplicationDAO.getLeaveApplication(mockLeaveApplication.getApplicationId());

    assertEquals(
        mockLeaveApplication.getApplicationId(), actualLeaveApplication.getApplicationId());
    assertEquals(mockLeaveApplication.getStartDate(), actualLeaveApplication.getStartDate());
    assertEquals(mockLeaveApplication.getEndDate(), actualLeaveApplication.getEndDate());
    assertEquals(mockLeaveApplication.getMessage(), actualLeaveApplication.getMessage());
    assertEquals(
        mockLeaveApplication.getLeaveType().getLeaveTypeId(),
        actualLeaveApplication.getLeaveType().getLeaveTypeId());
  }

  @Test(expected = ApplicationDoesNotExistException.class)
  public void getLeaveApplicationShouldHandle404Error() throws ApplicationDoesNotExistException {
    leaveApplicationDAO.getLeaveApplication(199L);
  }

  @Test
  public void createLeaveApplicationShouldAddEntryToDB() {
    final Session session = sessionFactory.getCurrentSession();
    leaveApplicationDAO.createLeaveApplication(mockEmployee, mockLeaveApplication);
    final LeaveApplication actualLeaveApplication =
        session.get(LeaveApplication.class, mockLeaveApplication.getApplicationId());

    assertEquals(
        mockLeaveApplication.getApplicationId(), actualLeaveApplication.getApplicationId());
    assertEquals(mockLeaveApplication.getStartDate(), actualLeaveApplication.getStartDate());
    assertEquals(mockLeaveApplication.getEndDate(), actualLeaveApplication.getEndDate());
    assertEquals(mockLeaveApplication.getMessage(), actualLeaveApplication.getMessage());
    assertEquals(
        mockLeaveApplication.getLeaveType().getLeaveTypeId(),
        actualLeaveApplication.getLeaveType().getLeaveTypeId());
    assertEquals(
        mockLeaveApplication.isApprovedByManager(), actualLeaveApplication.isApprovedByManager());
    assertEquals(mockLeaveApplication.isApprovedByHR(), actualLeaveApplication.isApprovedByHR());
    assertEquals(
        mockLeaveApplication.getProcessInstanceId(), actualLeaveApplication.getProcessInstanceId());
  }

  @Test
  public void updateLeaveApplicationShouldUpdateApplication()
      throws ApplicationDoesNotExistException {
    mockLeaveApplication.setStartDate(LocalDate.of(2018, 5, 5));
    mockLeaveApplication.setEndDate(LocalDate.of(2018, 5, 10));
    mockLeaveApplication.setApprovedByManager(true);
    mockLeaveApplication.setApprovedByHR(true);

    final Session session = sessionFactory.getCurrentSession();
    session.save(mockLeaveApplication);
    final LeaveApplication actualUpdatedApplication =
        leaveApplicationDAO.updateLeaveApplication(
            mockLeaveApplication.getApplicationId(), mockLeaveApplication);

    assertEquals(
        mockLeaveApplication.getApplicationId(), actualUpdatedApplication.getApplicationId());
    assertEquals(mockLeaveApplication.getStartDate(), actualUpdatedApplication.getStartDate());
    assertEquals(mockLeaveApplication.getEndDate(), actualUpdatedApplication.getEndDate());
    assertEquals(mockLeaveApplication.getMessage(), actualUpdatedApplication.getMessage());
    assertEquals(mockLeaveApplication.getLeaveType(), actualUpdatedApplication.getLeaveType());
    assertEquals(
        mockLeaveApplication.isApprovedByManager(), actualUpdatedApplication.isApprovedByManager());
    assertEquals(mockLeaveApplication.isApprovedByHR(), actualUpdatedApplication.isApprovedByHR());
  }

  @Test(expected = ApplicationDoesNotExistException.class)
  public void updateLeaveApplicationShouldHandle404Error() throws ApplicationDoesNotExistException {
    final LeaveApplication leaveApplication = new LeaveApplication();
    leaveApplicationDAO.updateLeaveApplication(
        leaveApplication.getApplicationId(), leaveApplication);
  }
}
