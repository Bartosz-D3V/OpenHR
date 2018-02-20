package org.openhr.application.user.facade;

import org.openhr.application.user.domain.User;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.common.exception.UserDoesNotExist;

public interface UserFacade {
  User findByUsername(String username) throws UserDoesNotExist;

  void registerUser(User user) throws UserAlreadyExists;
}
