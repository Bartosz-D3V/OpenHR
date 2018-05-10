package org.openhr.application.manager.repository;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.manager.dao.ManagerDAO;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ManagerRepositoryTest {
  private static final Manager mockManager =
      new Manager(
          new PersonalInformation("John", "Black", "Alex", LocalDate.now()),
          new ContactInformation(),
          new EmployeeInformation(),
          new HrInformation(),
          new User("421", ""));

  @Autowired private SessionFactory sessionFactory;

  @Autowired private ManagerDAO managerDAO;

  @Test
  public void getManagerShouldReturnManagerById() throws SubjectDoesNotExistException {
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockManager);
    final Manager manager = managerDAO.getManager(mockManager.getSubjectId());

    assertEquals(mockManager.getSubjectId(), manager.getSubjectId());
  }
}
