package org.openhr.application.user.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserDAOTest {
  @Autowired private UserRepository userRepository;

  @Autowired private SessionFactory sessionFactory;

  @Test
  public void registerUserShouldPersistUserObject() {
    final User mockUser = new User("username2", "password");
    userRepository.registerUser(mockUser);
    final Session session = sessionFactory.getCurrentSession();
    final User actualUser = session.get(User.class, mockUser.getUserId());

    assertNotEquals(0, actualUser.getUserId());
    assertEquals(mockUser.getUsername(), actualUser.getUsername());
    assertEquals(mockUser.getPassword(), actualUser.getPassword());
  }
}
