package org.openhr.repository.user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class UserRepositoryTest {
  private final static User mockUser = new User("username", "password");

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SessionFactory sessionFactory;

  @Test
  public void findByUsernameShouldReturnUserObject() {
    final Session session = sessionFactory.openSession();
    session.save(mockUser);
    session.close();
    final User actualUser = userRepository.findByUsername(mockUser.getUsername());

    assertNotEquals(0, actualUser.getUserId());
    assertEquals(mockUser.getUsername(), actualUser.getUsername());
    assertEquals(mockUser.getPassword(), actualUser.getPassword());
  }

  @Test
  public void registerUserShouldPersistUserObject() {
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
    final Session session = sessionFactory.openSession();
    session.save(mockUser);
    session.close();
    final String password = userRepository.getEncodedPassword(mockUser.getUsername());

    assertEquals(mockUser.getPassword(), password);
  }
}
