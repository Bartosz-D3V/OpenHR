package org.openhr.application.user.facade;

import org.openhr.application.user.domain.User;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.application.user.service.UserService;
import org.openhr.common.exception.UserDoesNotExist;
import org.springframework.stereotype.Component;

@Component
public class UserFacadeImpl implements UserFacade {
  private final UserService userService;

  public UserFacadeImpl(final UserService userService) {
    this.userService = userService;
  }

  @Override
  public User findByUsername(final String username) throws UserDoesNotExist {
    return userService.findByUsername(username);
  }

  @Override
  public void registerUser(final User user) throws UserAlreadyExists {
    userService.registerUser(user);
  }

  @Override
  public boolean isUsernameFree(final String username) {
    return userService.isUsernameFree(username);
  }
}
