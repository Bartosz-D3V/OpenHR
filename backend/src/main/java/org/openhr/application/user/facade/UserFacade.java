package org.openhr.application.user.facade;

import org.openhr.application.user.domain.User;
import org.openhr.application.user.dto.PasswordDTO;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.common.exception.UserDoesNotExist;
import org.openhr.common.exception.ValidationException;

public interface UserFacade {
  void registerUser(User user) throws UserAlreadyExists;

  void updatePassword(long subjectId, PasswordDTO passwordDTO) throws UserDoesNotExist, ValidationException;

  User findByUsername(String username) throws UserDoesNotExist;
}
