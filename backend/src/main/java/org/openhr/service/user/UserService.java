package org.openhr.service.user;

import org.openhr.domain.user.User;
import org.openhr.exception.UserAlreadyExists;
import org.openhr.exception.UserDoesNotExist;

public interface UserService {
  User findByUsername(String username) throws UserDoesNotExist;

  void registerUser(User user) throws UserAlreadyExists, UserDoesNotExist;

  String getEncodedPassword(long userId);

  String getEncodedPassword(String username);

  boolean validCredentials(String username, String password) throws UserDoesNotExist;

  boolean usernameIsFree(String username);
}
