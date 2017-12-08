package org.openhr.dao.leaveapplication;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.domain.address.Address;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.subject.ContactInformation;
import org.openhr.domain.subject.EmployeeInformation;
import org.openhr.domain.subject.PersonalInformation;
import org.openhr.domain.subject.Subject;
import org.openhr.enumeration.Role;
import org.openhr.exception.SubjectDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LeaveApplicationDAOTest {
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
  private SessionFactory sessionFactory;

  @Autowired
  private LeaveApplicationDAO leaveApplicationDAO;

  @Test
  public void createLeaveApplicationShouldAddEntryToDB() {
    LeaveApplication actualLeaveApplication;
    leaveApplicationDAO.createLeaveApplication(mockSubject, mockLeaveApplication);
    final Session session = sessionFactory.openSession();
    session.save(mockSubject);
    actualLeaveApplication = session.get(LeaveApplication.class, mockLeaveApplication.getApplicationId());
    session.close();

    assertEquals(mockLeaveApplication.getApplicationId(), actualLeaveApplication.getApplicationId());
    assertEquals(mockLeaveApplication.getStartDate(), actualLeaveApplication.getStartDate());
    assertEquals(mockLeaveApplication.getEndDate(), actualLeaveApplication.getEndDate());
    assertEquals(mockLeaveApplication.getMessage(), actualLeaveApplication.getMessage());
    assertEquals(mockLeaveApplication.getLeaveType(), actualLeaveApplication.getLeaveType());
  }
}
