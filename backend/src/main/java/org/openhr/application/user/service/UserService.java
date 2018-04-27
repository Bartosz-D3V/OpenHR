package org.openhr.application.user.service;

import java.util.List;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.domain.UserRole;
import org.openhr.application.user.dto.PasswordDTO;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.common.exception.UserDoesNotExist;
import org.openhr.common.exception.ValidationException;

public interface UserService {
  User getUser(long userId);

  User getUserByUsername(String username) throws UserDoesNotExist;

  User updateUser(long userId, User user);

  void registerUser(User user) throws UserAlreadyExists;

  User getUserBySubjectId(long subjectId);

  void updateNotificationsSettings(long userId, boolean notificationsTurnedOn);

  void updatePassword(long userId, PasswordDTO passwordDTO)
      throws UserDoesNotExist, ValidationException, SubjectDoesNotExistException;

  boolean validCredentials(String username, String password) throws UserDoesNotExist;

  boolean isUsernameFree(String username);

  long findSubjectId(String username) throws SubjectDoesNotExistException;

  boolean notificationsEnabled(long userId);

  String encodePassword(String password);

  boolean passwordsMatch(String rawPassword, String encodedPassword);

  List<UserRole> setBasicUserRoles(User user);

  List<UserRole> setManagerUserRole(User user);

  List<UserRole> setHrUserRole(User user);
}
