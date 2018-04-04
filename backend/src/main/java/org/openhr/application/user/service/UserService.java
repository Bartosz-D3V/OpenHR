package org.openhr.application.user.service;

import org.openhr.application.user.domain.User;
import org.openhr.application.user.dto.PasswordDTO;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.common.exception.UserDoesNotExist;
import org.openhr.common.exception.ValidationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
  User getUserBySubjectId(long subjectId) throws UserDoesNotExist;

  User getUserByUsername(String username) throws UserDoesNotExist;

  User updateUser(long userId, User user);

  void registerUser(User user) throws UserAlreadyExists;

  void updatePassword(long subjectId, PasswordDTO passwordDTO) throws UserDoesNotExist, ValidationException;

  boolean validCredentials(String username, String password) throws UserDoesNotExist;

  boolean isUsernameFree(String username);

  long findSubjectId(String username) throws SubjectDoesNotExistException;
}
