package org.openhr.application.user.repository;

import org.openhr.application.user.domain.User;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserDoesNotExist;

import java.util.List;

public interface UserRepository {
  User findByUsername(String username);

  long findUserId(String username);

  void registerUser(User user);

  String getEncodedPassword(long userId) throws UserDoesNotExist;

  List<String> retrieveUsernamesInUse();

  long findSubjectId(String username) throws SubjectDoesNotExistException;
}
