package org.openhr.application.user.service;

import org.openhr.application.user.domain.User;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.common.exception.UserDoesNotExist;

public interface UserService {
  User findByUsername(String username) throws UserDoesNotExist;

  void registerUser(User user) throws UserAlreadyExists;

  String getEncodedPassword(long userId);

  String getEncodedPassword(String username);

  boolean validCredentials(String username, String password) throws UserDoesNotExist;

  boolean isUsernameFree(String username);
}
