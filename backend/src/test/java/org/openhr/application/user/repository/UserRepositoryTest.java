package org.openhr.application.user.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserDoesNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryTest {
  @Autowired private UserRepository userRepository;

  @Autowired private SessionFactory sessionFactory;

  @Test
  public void getUserBySubjectIdShouldReturnAssociatedUserObject() {
    final Employee employee =
        new Employee(
            new PersonalInformation("John", "Test", null, null),
            new ContactInformation(),
            new EmployeeInformation(),
            new HrInformation(),
            new User("root1", "password"));
    final Session session = sessionFactory.getCurrentSession();
    session.save(employee);
    session.flush();

    final User user = userRepository.getUserBySubjectId(employee.getSubjectId());
    final Employee actualEmployee = session.get(Employee.class, employee.getSubjectId());

    assertNotNull(user);
    assertNotEquals(0, user.getUserId());
    assertEquals(actualEmployee.getUser(), user);
  }

  @Test
  public void findByUsernameShouldReturnUserObject() {
    final User mockUser = new User("username1", "password");
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockUser);
    final User actualUser = userRepository.getUserByUsername(mockUser.getUsername());

    assertNotEquals(0, actualUser.getUserId());
    assertEquals(mockUser.getUsername(), actualUser.getUsername());
    assertEquals(mockUser.getPassword(), actualUser.getPassword());
  }

  @Test
  public void findUserIdShouldReturnIdOfUserByUsername() {
    final User mockUser = new User("John64", "password");
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockUser);
    final long actualUserId = userRepository.findUserId(mockUser.getUsername());

    assertNotEquals(0, actualUserId);
    assertEquals(mockUser.getUserId(), actualUserId);
  }

  @Test
  public void getEncodedPasswordShouldReturnEncodedPassword() throws UserDoesNotExist {
    final User mockUser = new User("username3", "password");
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockUser);
    final String password = userRepository.getEncodedPassword(mockUser.getUserId());

    assertEquals(mockUser.getPassword(), password);
  }

  @Test(expected = UserDoesNotExist.class)
  public void getEncodedPasswordShouldThrowErrorIfUserDoesNotExist() throws UserDoesNotExist {
    userRepository.getEncodedPassword(1000L);
  }

  @Test
  @Ignore("Test class is not transactional - needs to be fixed")
  public void retrieveUsernamesInUseShouldReturnListOfUsernamesInUse() {
    final User mockUser1 = new User("Curie", "password");
    final User mockUser2 = new User("Heisenberg", "password");
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockUser1);
    session.save(mockUser2);
    final List<String> actualUsernamesInUse = userRepository.retrieveUsernamesInUse();

    assertEquals(2, actualUsernamesInUse.size());
    assertEquals(mockUser1.getUsername(), actualUsernamesInUse.get(0));
    assertEquals(mockUser2.getUsername(), actualUsernamesInUse.get(1));
  }

  @Test
  public void findSubjectIdShouldReturnSubjectIdByUsername() throws SubjectDoesNotExistException {
    final User mockUser1 = new User("Kopernik", "password");
    final Subject subject = new Employee();
    subject.setPersonalInformation(
        new PersonalInformation("John", "Black", "Alex", LocalDate.now()));
    subject.setUser(mockUser1);
    subject.setContactInformation(new ContactInformation());
    subject.setEmployeeInformation(new EmployeeInformation());
    subject.setHrInformation(new HrInformation());
    final Session session = sessionFactory.getCurrentSession();
    session.save(subject);
    session.flush();
    final long subjectId = userRepository.findSubjectId(mockUser1.getUsername());

    assertNotEquals(0, mockUser1.getUsername());
    assertNotEquals(0, subject.getSubjectId());
    assertEquals(subject.getSubjectId(), subjectId);
  }

  @Test(expected = SubjectDoesNotExistException.class)
  public void findSubjectIdShouldThrowErrorIfUserWasNotFound() throws SubjectDoesNotExistException {
    userRepository.findSubjectId("ThisUsernameDoesNotExist");
  }
}
