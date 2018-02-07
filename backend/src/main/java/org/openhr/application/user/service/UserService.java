package org.openhr.application.user.service;

import org.openhr.application.user.domain.User;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.common.exception.UserDoesNotExist;

public interface UserService {
  User findByUsername(String username) throws UserDoesNotExist;

  void registerUser(User user) throws UserAlreadyExists;

  String getEncodedPassword(long userId) throws UserDoesNotExist;

  String getEncodedPassword(String username) throws UserDoesNotExist;

  boolean validCredentials(String username, String password) throws UserDoesNotExist;

  boolean isUsernameFree(String username);

  long findSubjectId(String username) throws SubjectDoesNotExistException;
}
