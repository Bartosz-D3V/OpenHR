package org.openhr.application.user.service;

import org.openhr.application.user.domain.User;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.common.exception.UserDoesNotExist;
import org.openhr.application.user.repository.UserRepository;
import org.openhr.application.authentication.service.AuthenticationService;
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
    final boolean isUsernameFree = isUsernameFree(user.getUsername());
    if (!isUsernameFree) {
      throw new UserAlreadyExists("Provided username is already in use");
    }
    user.setPassword(authenticationService.encodePassword(user.getPassword()));
    userRepository.registerUser(user);
  }

  @Override
  public String getEncodedPassword(final long userId) throws UserDoesNotExist {
    return userRepository.getEncodedPassword(userId);
  }

  @Override
  public String getEncodedPassword(final String username) throws UserDoesNotExist {
    final long userId = userRepository.findUserId(username);
    return userRepository.getEncodedPassword(userId);
  }

  @Override
  public boolean validCredentials(final String username, final String password) {
    final User user = userRepository.findByUsername(username);
    return user != null && authenticationService.passwordsMatch(password, user.getPassword());
  }

  @Override
  public boolean isUsernameFree(final String username) {
    return !userRepository.retrieveUsernamesInUse().contains(username);
  }

  @Override
  public long findSubjectId(final String username) throws SubjectDoesNotExistException {
    return userRepository.findSubjectId(username);
  }
}
