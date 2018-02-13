package org.openhr.application.user.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.repository.UserRepository;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.common.exception.UserDoesNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceTest {
  @Autowired
  private UserService userService;

  @MockBean
  private UserRepository userRepository;

  @Test(expected = UserDoesNotExist.class)
  public void findByUsernameShouldThrowUserDoesNotExistError() throws UserDoesNotExist {
    when(userRepository.findByUsername("test")).thenReturn(null);

    userService.findByUsername("test");
  }

  @Test(expected = UserAlreadyExists.class)
  public void registerUserShouldThrowUserAlreadyExistsError() throws UserAlreadyExists {
    final List<String> usernamesInUse = new ArrayList<>();
    usernamesInUse.add("Test_Test");
    when(userRepository.retrieveUsernamesInUse()).thenReturn(usernamesInUse);

    final User user = new User("Test_Test", "");
    userService.registerUser(user);
  }

  @Test
  public void isUsernameFreeShouldReturnTrueIfUsernameDoesNotExist() {
    when(userRepository.retrieveUsernamesInUse()).thenReturn(new ArrayList<>());

    assertTrue(userService.isUsernameFree("Test"));
  }

  @Test
  public void isUsernameFreeShouldReturnFalseIfUsernameExist() {
    final List<String> usernamesInUse = new ArrayList<>();
    usernamesInUse.add("Test_Test");
    when(userRepository.retrieveUsernamesInUse()).thenReturn(usernamesInUse);

    assertFalse(userService.isUsernameFree("Test_Test"));
  }
}
