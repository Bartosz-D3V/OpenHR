package org.openhr.service.user;

import org.openhr.domain.user.User;
import org.openhr.exception.UserAlreadyExists;
import org.openhr.exception.UserDoesNotExist;
import org.openhr.repository.user.UserRepository;
import org.openhr.service.authentication.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final AuthenticationService authenticationService;

  public UserServiceImpl(final UserRepository userRepository,
                         final AuthenticationService authenticationService) {
    this.userRepository = userRepository;
    this.authenticationService = authenticationService;
  }

  @Override
  public User findByUsername(final String username) throws UserDoesNotExist {
    final User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UserDoesNotExist("Requested user does not exist");
    }
    return user;
  }

  @Override
  public void registerUser(final User user) throws UserAlreadyExists {
    final boolean usernameIsFree = usernameIsFree(user.getUsername());
    if (!usernameIsFree) {
      throw new UserAlreadyExists("Provided username is already in use");
    }
    user.setPassword(authenticationService.encodePassword(user.getPassword()));
    userRepository.registerUser(user);
  }

  @Override
  public String getEncodedPassword(final long userId) {
    return userRepository.getEncodedPassword(userId);
  }

  @Override
  public String getEncodedPassword(final String username) {
    final long userId = userRepository.findUserId(username);
    return userRepository.getEncodedPassword(userId);
  }

  @Override
  public boolean validCredentials(final String username, final String password) throws UserDoesNotExist {
    final User user = findByUsername(username);
    return user != null && authenticationService.passwordsMatch(password, user.getPassword());
  }

  @Override
  public boolean usernameIsFree(final String username) {
    return userRepository.usernameIsFree(username);
  }
}
