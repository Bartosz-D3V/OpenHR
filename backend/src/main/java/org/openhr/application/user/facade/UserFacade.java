package org.openhr.application.user.facade;

import org.openhr.application.user.domain.User;
import org.openhr.application.user.dto.PasswordDTO;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.common.exception.UserDoesNotExist;
import org.openhr.common.exception.ValidationException;

public interface UserFacade {
  User getUser(long userId);

  void registerUser(User user) throws UserAlreadyExists;

  User updateUser(long userId, User user);

  User getUserBySubjectId(long subjectId);

  User getUserByUsername(String username) throws UserDoesNotExist;

  void updateNotificationsSettings(long userId, boolean notificationsTurnedOn);

  void updatePassword(long userId, PasswordDTO passwordDTO)
      throws UserDoesNotExist, ValidationException, SubjectDoesNotExistException;

  User findByUsername(String username) throws UserDoesNotExist;
}
