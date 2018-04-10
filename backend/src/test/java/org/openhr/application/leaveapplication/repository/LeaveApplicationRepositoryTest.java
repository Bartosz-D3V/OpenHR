package org.openhr.application.leaveapplication.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;
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
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LeaveApplicationRepositoryTest {
  private final LeaveType leaveType1 = new LeaveType("Holiday", "Annual Leave");
  private final LeaveType leaveType2 = new LeaveType("Maternity Leave", "One Year Maternity Leave");
  private final Manager mockManager =
      new Manager(
          new PersonalInformation("John", "Black", "Alex", LocalDate.now()),
          new ContactInformation(),
          new EmployeeInformation(),
          new HrInformation(),
          new User("", ""));
  private final Employee mockEmployee =
      new Employee(
          new PersonalInformation("John", "Black", "Alex", LocalDate.now()),
          new ContactInformation(),
          new EmployeeInformation(),
          new HrInformation(),
          new User("1", ""));

  @Autowired private LeaveApplicationRepository leaveApplicationRepository;

  @Autowired private SessionFactory sessionFactory;

  @Before
  public void setUp() {
    final Session session = sessionFactory.getCurrentSession();
    session.save(leaveType1);
    session.save(leaveType2);
  }

  @Test
  public void getSubjectsLeaveApplicationsShouldReturnSubjectsApplications() {
    final LeaveApplication leaveApplication1 = new LeaveApplication();
    leaveApplication1.setLeaveType(leaveType1);
    leaveApplication1.setStartDate(LocalDate.now());
    leaveApplication1.setEndDate(LocalDate.now());
    leaveApplication1.setSubject(mockManager);
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockManager);
    session.save(leaveApplication1);
    session.flush();
    final List<LeaveApplication> actualLeaveApplications =
        leaveApplicationRepository.getSubjectsLeaveApplications(mockManager.getSubjectId());

    assertEquals(1, actualLeaveApplications.size());
    assertEquals(leaveApplication1, actualLeaveApplications.get(0));
  }

  @Test
  public void
      getSubjectsLeaveApplicationsShouldReturnEmptyListIfNoApplicationCreatedBySubjectFound() {
    final LeaveApplication leaveApplication1 = new LeaveApplication();
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockManager);
    session.save(mockEmployee);
    leaveApplication1.setLeaveType(leaveType1);
    leaveApplication1.setStartDate(LocalDate.now());
    leaveApplication1.setEndDate(LocalDate.now());
    leaveApplication1.setSubject(mockEmployee);
    session.save(leaveApplication1);
    final List<LeaveApplication> actualLeaveApplications =
        leaveApplicationRepository.getSubjectsLeaveApplications(mockManager.getSubjectId());

    assertEquals(0, actualLeaveApplications.size());
  }

  @Test
  public void getLeaveTypesShouldReturnListOfPreDefinedLeaveTypesAndLeaveTypesAdded() {
    assertTrue(leaveApplicationRepository.getLeaveTypes().size() >= 2);
  }

  @Test
  public void dateRangeAlreadyBookedShouldReturnTrueIfRequestedLeaveDatesOverlapsWithDBEntries() {
    final LeaveApplication leaveApplication1 = new LeaveApplication();
    leaveApplication1.setStartDate(LocalDate.of(2020, 5, 5));
    leaveApplication1.setEndDate(LocalDate.of(2020, 5, 10));
    leaveApplication1.setLeaveType(leaveType1);
    leaveApplication1.setApprovedByManager(true);
    leaveApplication1.setApprovedByHR(true);
    leaveApplication1.setSubject(mockManager);
    final LeaveApplication leaveApplication2 = new LeaveApplication();
    leaveApplication2.setStartDate(LocalDate.of(2020, 5, 11));
    leaveApplication2.setEndDate(LocalDate.of(2020, 5, 13));
    leaveApplication2.setLeaveType(leaveType1);
    leaveApplication2.setApprovedByManager(true);
    leaveApplication2.setApprovedByHR(true);
    leaveApplication2.setSubject(mockManager);
    final LeaveApplication actualLeaveApplication1 = new LeaveApplication();
    actualLeaveApplication1.setStartDate(LocalDate.of(2020, 5, 7));
    actualLeaveApplication1.setEndDate(LocalDate.of(2020, 5, 8));
    actualLeaveApplication1.setLeaveType(leaveType1);
    final LeaveApplication actualLeaveApplication2 = new LeaveApplication();
    actualLeaveApplication2.setStartDate(LocalDate.of(2020, 5, 2));
    actualLeaveApplication2.setEndDate(LocalDate.of(2020, 5, 18));
    actualLeaveApplication2.setLeaveType(leaveType1);
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockEmployee);
    session.save(mockManager);
    session.save(leaveApplication1);
    session.flush();
    session.clear();
    session.save(leaveApplication2);

    assertTrue(
        leaveApplicationRepository.dateRangeAlreadyBooked(
            mockManager.getSubjectId(),
            actualLeaveApplication1.getStartDate(),
            actualLeaveApplication1.getEndDate()));
    assertTrue(
        leaveApplicationRepository.dateRangeAlreadyBooked(
            mockManager.getSubjectId(),
            actualLeaveApplication2.getStartDate(),
            actualLeaveApplication2.getEndDate()));
  }

  @Test
  public void
      dateRangeAlreadyBookedShouldReturnFalseIfRequestedLeaveDatesDoNotOverlapWithDBEntries() {
    final LeaveApplication leaveApplication1 = new LeaveApplication();
    leaveApplication1.setStartDate(LocalDate.of(2020, 5, 5));
    leaveApplication1.setEndDate(LocalDate.of(2020, 5, 10));
    leaveApplication1.setLeaveType(leaveType1);
    leaveApplication1.setApprovedByManager(true);
    leaveApplication1.setApprovedByHR(true);
    leaveApplication1.setSubject(mockManager);
    final LeaveApplication leaveApplication2 = new LeaveApplication();
    leaveApplication2.setStartDate(LocalDate.of(2020, 5, 11));
    leaveApplication2.setEndDate(LocalDate.of(2020, 5, 13));
    leaveApplication2.setLeaveType(leaveType1);
    leaveApplication2.setApprovedByManager(true);
    leaveApplication2.setApprovedByHR(true);
    leaveApplication2.setSubject(mockManager);
    final LeaveApplication actualLeaveApplication1 = new LeaveApplication();
    actualLeaveApplication1.setStartDate(LocalDate.of(2020, 5, 14));
    actualLeaveApplication1.setEndDate(LocalDate.of(2020, 5, 15));
    actualLeaveApplication1.setLeaveType(leaveType1);
    actualLeaveApplication1.setSubject(mockManager);
    final LeaveApplication actualLeaveApplication2 = new LeaveApplication();
    actualLeaveApplication2.setStartDate(LocalDate.of(2020, 5, 3));
    actualLeaveApplication2.setEndDate(LocalDate.of(2020, 5, 4));
    actualLeaveApplication2.setLeaveType(leaveType1);
    actualLeaveApplication2.setSubject(mockManager);
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockEmployee);
    session.save(mockManager);
    session.save(leaveApplication1);
    session.flush();
    session.clear();
    session.save(leaveApplication2);

    assertFalse(
        leaveApplicationRepository.dateRangeAlreadyBooked(
            mockManager.getSubjectId(),
            actualLeaveApplication1.getStartDate(),
            actualLeaveApplication1.getEndDate()));
    assertFalse(
        leaveApplicationRepository.dateRangeAlreadyBooked(
            mockManager.getSubjectId(),
            actualLeaveApplication2.getStartDate(),
            actualLeaveApplication2.getEndDate()));
  }

  @Test
  public void
      getAwaitingForManagerLeaveApplicationsShouldReturnAssignedToManagerUnfinishedLeaveApplications() {
    final LeaveApplication leaveApplication1 = new LeaveApplication();
    leaveApplication1.setStartDate(LocalDate.of(2020, 5, 5));
    leaveApplication1.setEndDate(LocalDate.of(2020, 5, 10));
    leaveApplication1.setLeaveType(leaveType1);
    leaveApplication1.setApprovedByManager(false);
    leaveApplication1.setApprovedByHR(false);
    leaveApplication1.setSubject(mockManager);
    leaveApplication1.setAssignee(mockManager);
    final LeaveApplication leaveApplication2 = new LeaveApplication();
    leaveApplication2.setStartDate(LocalDate.of(2020, 5, 11));
    leaveApplication2.setEndDate(LocalDate.of(2020, 5, 15));
    leaveApplication2.setLeaveType(leaveType1);
    leaveApplication2.setApprovedByManager(true);
    leaveApplication2.setSubject(mockManager);
    leaveApplication2.setAssignee(null);
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockEmployee);
    session.save(mockManager);
    session.save(leaveApplication1);
    session.flush();
    session.clear();
    session.save(leaveApplication2);

    final List<LeaveApplication> actualLeaveApplications =
        leaveApplicationRepository.getAwaitingForActionLeaveApplications(
            mockManager.getSubjectId());
    final LeaveApplication expectedLeaveApplication =
        session.get(LeaveApplication.class, leaveApplication1.getApplicationId());

    assertEquals(1, actualLeaveApplications.size());
    assertEquals(expectedLeaveApplication, actualLeaveApplications.get(0));
  }
}
