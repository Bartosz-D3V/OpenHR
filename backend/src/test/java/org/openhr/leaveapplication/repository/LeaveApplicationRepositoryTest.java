package org.openhr.leaveapplication.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.leaveapplication.repository.LeaveApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class LeaveApplicationRepositoryTest {
  @Autowired
  private LeaveApplicationRepository leaveApplicationRepository;

  @Autowired
  private SessionFactory sessionFactory;

  @Test
  public void getLeaveTypesShouldReturnListOfPreDefinedLeaveTypes() {
    assertEquals(5, leaveApplicationRepository.getLeaveTypes().size());
  }
}
