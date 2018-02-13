package org.openhr.application.leaveapplication.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class LeaveApplicationRepositoryTest {
  private final static LeaveType leaveType1 = new LeaveType("Holiday", "Annual Leave");
  private final static LeaveType leaveType2 = new LeaveType("Maternity Leave", "One Year Maternity Leave");
  private final static Subject mockSubject = new Subject("Alex", "White", new PersonalInformation(),
    new ContactInformation(), new EmployeeInformation(), new HrInformation(), new User("", ""));

  @Autowired
  private LeaveApplicationRepository leaveApplicationRepository;

  @Autowired
  private SessionFactory sessionFactory;

  @Before
  public void setUp() {
    final Session session = sessionFactory.getCurrentSession();
    session.save(leaveType1);
    session.save(leaveType2);
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
    leaveApplication1.setSubject(mockSubject);
    leaveApplication1.setApprovedByManager(true);
    leaveApplication1.setApprovedByHR(true);
    final LeaveApplication leaveApplication2 = new LeaveApplication();
    leaveApplication2.setStartDate(LocalDate.of(2020, 5, 11));
    leaveApplication2.setEndDate(LocalDate.of(2020, 5, 13));
    leaveApplication2.setLeaveType(leaveType1);
    leaveApplication2.setSubject(mockSubject);
    leaveApplication2.setApprovedByManager(true);
    leaveApplication2.setApprovedByHR(true);
    final LeaveApplication actualLeaveApplication1 = new LeaveApplication();
    actualLeaveApplication1.setStartDate(LocalDate.of(2020, 5, 7));
    actualLeaveApplication1.setEndDate(LocalDate.of(2020, 5, 8));
    actualLeaveApplication1.setLeaveType(leaveType1);
    actualLeaveApplication1.setSubject(mockSubject);
    final LeaveApplication actualLeaveApplication2 = new LeaveApplication();
    actualLeaveApplication2.setStartDate(LocalDate.of(2020, 5, 2));
    actualLeaveApplication2.setEndDate(LocalDate.of(2020, 5, 18));
    actualLeaveApplication2.setLeaveType(leaveType1);
    actualLeaveApplication2.setSubject(mockSubject);
    final Session session = sessionFactory.getCurrentSession();
    session.save(leaveApplication1);
    session.save(leaveApplication2);

    assertTrue(leaveApplicationRepository.dateRangeAlreadyBooked(mockSubject.getSubjectId(),
      actualLeaveApplication1.getStartDate(), actualLeaveApplication1.getEndDate()));
    assertTrue(leaveApplicationRepository.dateRangeAlreadyBooked(mockSubject.getSubjectId(),
      actualLeaveApplication2.getStartDate(), actualLeaveApplication2.getEndDate()));
  }

  @Test
  public void dateRangeAlreadyBookedShouldReturnFalseIfRequestedLeaveDatesDoNotOverlapWithDBEntries() {
    final LeaveApplication leaveApplication1 = new LeaveApplication();
    leaveApplication1.setStartDate(LocalDate.of(2020, 5, 5));
    leaveApplication1.setEndDate(LocalDate.of(2020, 5, 10));
    leaveApplication1.setLeaveType(leaveType1);
    leaveApplication1.setSubject(mockSubject);
    leaveApplication1.setApprovedByManager(true);
    leaveApplication1.setApprovedByHR(true);
    final LeaveApplication leaveApplication2 = new LeaveApplication();
    leaveApplication2.setStartDate(LocalDate.of(2020, 5, 11));
    leaveApplication2.setEndDate(LocalDate.of(2020, 5, 13));
    leaveApplication2.setLeaveType(leaveType1);
    leaveApplication2.setSubject(mockSubject);
    leaveApplication2.setApprovedByManager(true);
    leaveApplication2.setApprovedByHR(true);
    final LeaveApplication actualLeaveApplication1 = new LeaveApplication();
    actualLeaveApplication1.setStartDate(LocalDate.of(2020, 5, 14));
    actualLeaveApplication1.setEndDate(LocalDate.of(2020, 5, 15));
    actualLeaveApplication1.setLeaveType(leaveType1);
    actualLeaveApplication1.setSubject(mockSubject);
    final LeaveApplication actualLeaveApplication2 = new LeaveApplication();
    actualLeaveApplication2.setStartDate(LocalDate.of(2020, 5, 3));
    actualLeaveApplication2.setEndDate(LocalDate.of(2020, 5, 4));
    actualLeaveApplication2.setLeaveType(leaveType1);
    actualLeaveApplication2.setSubject(mockSubject);
    final Session session = sessionFactory.getCurrentSession();
    session.save(leaveApplication1);
    session.save(leaveApplication2);

    assertFalse(leaveApplicationRepository.dateRangeAlreadyBooked(mockSubject.getSubjectId(),
      actualLeaveApplication1.getStartDate(), actualLeaveApplication1.getEndDate()));
    assertFalse(leaveApplicationRepository.dateRangeAlreadyBooked(mockSubject.getSubjectId(),
      actualLeaveApplication2.getStartDate(), actualLeaveApplication2.getEndDate()));
  }
}