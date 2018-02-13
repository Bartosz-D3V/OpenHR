package org.openhr.application.leaveapplication.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.address.Address;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class LeaveApplicationDAOTest {
  private final static Address mockAddress = new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL", "London",
    "UK");
  private final static PersonalInformation mockPersonalInformation = new PersonalInformation("John", null);
  private final static ContactInformation mockContactInformation = new ContactInformation("0123456789", "j.x@g.com",
    mockAddress);
  private final static EmployeeInformation mockEmployeeInformation = new EmployeeInformation("S8821 B", "Tester",
    "Core", "12A", null, null);
  private final static HrInformation mockHrInformation = new HrInformation(25L);
  private final static Subject mockSubject = new Subject("John", "Xavier", mockPersonalInformation,
    mockContactInformation, mockEmployeeInformation, mockHrInformation, new User("Mck40", "testPass"));
  private final static LeaveApplication mockLeaveApplication = new LeaveApplication(LocalDate.now(), LocalDate.now().plusDays(5));
  private final static LeaveType leaveType = new LeaveType("Annual Leave", "Just a annual leave you've waited for!");

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private LeaveApplicationDAO leaveApplicationDAO;

  @Before
  public void setUp() {
    final Session session = sessionFactory.openSession();
    mockLeaveApplication.setProcessInstanceId(String.valueOf(5L));
    mockLeaveApplication.setApprovedByManager(true);
    mockLeaveApplication.setApprovedByHR(false);
    mockLeaveApplication.setSubject(mockSubject);
    mockLeaveApplication.setMessage("I am going to Vanuatu!");
    session.save(leaveType);
    mockLeaveApplication.setLeaveType(leaveType);
    session.close();
  }

  @Test
  public void getLeaveApplicationShouldReturnApplication() throws ApplicationDoesNotExistException {
    final Session session = sessionFactory.openSession();
    mockLeaveApplication.setSubject(mockSubject);
    session.save(mockLeaveApplication);
    session.close();
    final LeaveApplication actualLeaveApplication = leaveApplicationDAO.
      getLeaveApplication(mockLeaveApplication.getApplicationId());

    assertEquals(mockLeaveApplication.getApplicationId(), actualLeaveApplication.getApplicationId());
    assertEquals(mockLeaveApplication.getStartDate(), actualLeaveApplication.getStartDate());
    assertEquals(mockLeaveApplication.getEndDate(), actualLeaveApplication.getEndDate());
    assertEquals(mockLeaveApplication.getMessage(), actualLeaveApplication.getMessage());
    assertEquals(mockLeaveApplication.getLeaveType().getLeaveTypeId(), actualLeaveApplication.getLeaveType().getLeaveTypeId());
  }

  @Test(expected = ApplicationDoesNotExistException.class)
  public void getLeaveApplicationShouldHandle404Error() throws ApplicationDoesNotExistException {
    leaveApplicationDAO.getLeaveApplication(199L);
  }

  @Test
  public void createLeaveApplicationShouldAddEntryToDB() {
    LeaveApplication actualLeaveApplication;
    leaveApplicationDAO.createLeaveApplication(mockSubject, mockLeaveApplication);
    final Session session = sessionFactory.openSession();
    actualLeaveApplication = session.get(LeaveApplication.class, mockLeaveApplication.getApplicationId());
    session.close();

    assertEquals(mockLeaveApplication.getApplicationId(), actualLeaveApplication.getApplicationId());
    assertEquals(mockLeaveApplication.getStartDate(), actualLeaveApplication.getStartDate());
    assertEquals(mockLeaveApplication.getEndDate(), actualLeaveApplication.getEndDate());
    assertEquals(mockLeaveApplication.getMessage(), actualLeaveApplication.getMessage());
    assertEquals(mockLeaveApplication.getLeaveType().getLeaveTypeId(), actualLeaveApplication.getLeaveType().getLeaveTypeId());
    assertEquals(mockLeaveApplication.isApprovedByManager(), actualLeaveApplication.isApprovedByManager());
    assertEquals(mockLeaveApplication.isApprovedByHR(), actualLeaveApplication.isApprovedByHR());
    assertEquals(mockLeaveApplication.getProcessInstanceId(), actualLeaveApplication.getProcessInstanceId());
  }

  @Test
  public void updateLeaveApplicationShouldUpdateApplication() throws ApplicationDoesNotExistException {
    final LeaveApplication updatedLeaveApplication = mockLeaveApplication;
    updatedLeaveApplication.setStartDate(LocalDate.of(2018, 5, 5));
    updatedLeaveApplication.setEndDate(LocalDate.of(2018, 5, 10));
    updatedLeaveApplication.setApprovedByManager(true);
    updatedLeaveApplication.setApprovedByHR(true);

    final Session session = sessionFactory.openSession();
    mockLeaveApplication.setSubject(mockSubject);
    session.save(mockLeaveApplication);
    session.close();
    final LeaveApplication actualUpdatedApplication = leaveApplicationDAO.updateLeaveApplication(updatedLeaveApplication);

    assertEquals(mockLeaveApplication.getApplicationId(), actualUpdatedApplication.getApplicationId());
    assertEquals(mockLeaveApplication.getStartDate(), actualUpdatedApplication.getStartDate());
    assertEquals(mockLeaveApplication.getEndDate(), actualUpdatedApplication.getEndDate());
    assertEquals(mockLeaveApplication.getMessage(), actualUpdatedApplication.getMessage());
    assertEquals(mockLeaveApplication.getLeaveType(), actualUpdatedApplication.getLeaveType());
    assertEquals(mockLeaveApplication.isApprovedByManager(), actualUpdatedApplication.isApprovedByManager());
    assertEquals(mockLeaveApplication.isApprovedByHR(), actualUpdatedApplication.isApprovedByHR());
  }

  @Test(expected = ApplicationDoesNotExistException.class)
  public void updateLeaveApplicationShouldHandle404Error() throws ApplicationDoesNotExistException {
    final LeaveApplication leaveApplication = new LeaveApplication();
    leaveApplicationDAO.updateLeaveApplication(leaveApplication);
  }
}