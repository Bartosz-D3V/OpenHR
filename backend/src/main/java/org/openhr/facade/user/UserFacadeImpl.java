package org.openhr.facade.user;

import org.openhr.domain.user.User;
import org.openhr.service.authentication.AuthenticationService;
import org.openhr.service.user.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserFacadeImpl implements UserFacade {
  private final UserService userService;
  private final AuthenticationService authenticationService;

  public UserFacadeImpl(final UserService userService, final AuthenticationService authenticationService) {
    this.userService = userService;
    this.authenticationService = authenticationService;
  }

  @Override
  public User findByEmail(final String email) {
    return userService.findByEmail(email);
  }

  @Override
  public void registerUser(final User user) {
    user.setPassword(authenticationService.encodePassword(user.getPassword()));
    userService.registerUser(user);
  }
}
