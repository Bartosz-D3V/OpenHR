package org.openhr.leaveapplication.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.application.leaveapplication.repository.LeaveApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class LeaveApplicationRepositoryTest {
  @Autowired
  private LeaveApplicationRepository leaveApplicationRepository;

  @Autowired
  private SessionFactory sessionFactory;

  @Test
  public void getLeaveTypesShouldReturnListOfPreDefinedLeaveTypesAndLeaveTypesAdded() {
    final LeaveType leaveType1 = new LeaveType("Holiday", "Annual Leave");
    final LeaveType leaveType2 = new LeaveType("Maternity Leave", "One Year Maternity Leave");
    final Session session = sessionFactory.getCurrentSession();
    session.save(leaveType1);
    session.save(leaveType2);
    final List<LeaveType> leaveTypes = leaveApplicationRepository.getLeaveTypes();

    assertTrue(leaveTypes.size() >= 2);
  }
}
