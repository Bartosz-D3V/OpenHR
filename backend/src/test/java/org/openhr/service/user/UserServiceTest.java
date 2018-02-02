package org.openhr.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.domain.user.User;
import org.openhr.exception.UserAlreadyExists;
import org.openhr.exception.UserDoesNotExist;
import org.openhr.repository.user.UserRepository;
import org.openhr.service.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
  @Autowired
  private UserService userService;

  @MockBean
  private AuthenticationService authenticationService;

  @MockBean
  private UserRepository userRepository;

  @Test(expected = UserDoesNotExist.class)
  public void findByUsernameShouldReturnUserDoesNotExistIfUserDoesNotExist() throws UserDoesNotExist {
    when(userRepository.findByUsername("Mock")).thenReturn(null);
    userService.findByUsername("Mock");
  }

  @Test(expected = UserAlreadyExists.class)
  public void registerUserShouldReturnUserAlreadyExists() throws UserAlreadyExists {
    final User mockUser = new User("Mock", "1234");
    final List<String> mockUsernamesInUse = new ArrayList<>();
    mockUsernamesInUse.add(mockUser.getUsername());
    when(userRepository.retrieveUsernamesInUse()).thenReturn(mockUsernamesInUse);
    userService.registerUser(mockUser);
  }

  @Test
  public void validCredentialsShouldReturnFalseIfUserIsNull() throws UserDoesNotExist {
    when(userRepository.findByUsername("Mock")).thenReturn(null);
    assertFalse(userService.validCredentials("Mock", "123"));
  }

  @Test
  public void validCredentialsShouldReturnFalseIfUserPasswordsDoNotMatch() throws UserDoesNotExist {
    when(authenticationService.passwordsMatch(anyString(), anyString())).thenReturn(false);
    assertFalse(userService.validCredentials("Mock", "123"));
  }

  @Test
  public void validCredentialsShouldReturnTrueIfUserExistsAndPasswordsMatch() throws UserDoesNotExist {
    when(userRepository.findByUsername(anyString())).thenReturn(new User());
    when(authenticationService.passwordsMatch(anyString(), anyString())).thenReturn(true);

    assertTrue(userService.validCredentials("Mock", "123"));
  }

  @Test
  public void isUsernameFreeShouldReturnTrueIfRetrievedListDoesNotContainGivenUsername() {
    final List<String> usernames = new ArrayList<>();
    usernames.add("Pixel");
    usernames.add("Bl1nk");
    when(userRepository.retrieveUsernamesInUse()).thenReturn(usernames);

    assertTrue(userService.isUsernameFree("John"));
  }

  @Test
  public void isUsernameFreeShouldReturnFalseIfRetrievedListContainsGivenUsername() {
    final List<String> usernames = new ArrayList<>();
    usernames.add("Pixel");
    usernames.add("Bl1nk");
    when(userRepository.retrieveUsernamesInUse()).thenReturn(usernames);

    assertFalse(userService.isUsernameFree("Pixel"));
  }
}
