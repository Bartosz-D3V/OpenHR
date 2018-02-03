package org.openhr.user.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryTest {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SessionFactory sessionFactory;

  @After
  public void tearDown() {
    sessionFactory.getCurrentSession().flush();
    sessionFactory.getCurrentSession().clear();
  }

  @Test
  public void findByUsernameShouldReturnUserObject() {
    final User mockUser = new User("username1", "password");
    final Session session = sessionFactory.openSession();
    session.save(mockUser);
    session.close();
    final User actualUser = userRepository.findByUsername(mockUser.getUsername());

    assertNotEquals(0, actualUser.getUserId());
    assertEquals(mockUser.getUsername(), actualUser.getUsername());
    assertEquals(mockUser.getPassword(), actualUser.getPassword());
  }

  @Test
  public void findUserIdShouldReturnIdOfUserByUsername() {
    final User mockUser = new User("John64", "password");
    final Session session = sessionFactory.openSession();
    session.save(mockUser);
    session.close();
    final long actualUserId = userRepository.findUserId(mockUser.getUsername());

    assertNotEquals(0, actualUserId);
    assertEquals(mockUser.getUserId(), actualUserId);
  }

  @Test
  public void registerUserShouldPersistUserObject() {
    final User mockUser = new User("username2", "password");
    userRepository.registerUser(mockUser);
    final Session session = sessionFactory.openSession();
    final User actualUser = session.get(User.class, mockUser.getUserId());
    session.close();

    assertNotEquals(0, actualUser.getUserId());
    assertEquals(mockUser.getUsername(), actualUser.getUsername());
    assertEquals(mockUser.getPassword(), actualUser.getPassword());
  }

  @Test
  public void getEncodedPasswordShouldReturnEncodedPassword() {
    final User mockUser = new User("username3", "password");
    final Session session = sessionFactory.openSession();
    session.save(mockUser);
    session.close();
    final String password = userRepository.getEncodedPassword(mockUser.getUserId());

    assertEquals(mockUser.getPassword(), password);
  }

  @Test
  @Ignore("Test class is not transactional - needs to be fixed")
  public void retrieveUsernamesInUseShouldReturnListOfUsernamesInUse() {
    final User mockUser1 = new User("Kopernik", "password");
    final User mockUser2 = new User("Smith", "password");
    final Session session = sessionFactory.openSession();
    session.save(mockUser1);
    session.save(mockUser2);
    session.close();
    final List<String> actualUsernamesInUse = userRepository.retrieveUsernamesInUse();

    assertEquals(2, actualUsernamesInUse.size());
    assertEquals(mockUser1.getUsername(), actualUsernamesInUse.get(0));
    assertEquals(mockUser2.getUsername(), actualUsernamesInUse.get(1));
  }
}
