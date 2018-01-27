package org.openhr.facade.user;

import org.openhr.domain.user.User;
import org.openhr.exception.UserAlreadyExists;
import org.openhr.exception.UserDoesNotExist;

public interface UserFacade {
  User findByUsername(String username) throws UserDoesNotExist;

  void registerUser(User user) throws UserAlreadyExists;

  boolean isUsernameFree(String username);
}
