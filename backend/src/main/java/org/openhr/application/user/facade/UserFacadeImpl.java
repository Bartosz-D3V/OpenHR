package org.openhr.application.user.facade;

import org.openhr.application.user.domain.User;
import org.openhr.application.user.dto.PasswordDTO;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.application.user.service.UserService;
import org.openhr.common.exception.UserDoesNotExist;
import org.openhr.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserFacadeImpl implements UserFacade {
  private final UserService userService;

  public UserFacadeImpl(final UserService userService) {
    this.userService = userService;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void registerUser(final User user) throws UserAlreadyExists {
    userService.registerUser(user);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updatePassword(final long subjectId, final PasswordDTO passwordDTO)
    throws UserDoesNotExist, ValidationException {
    userService.updatePassword(subjectId, passwordDTO);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public User findByUsername(final String username) throws UserDoesNotExist {
    return userService.getUserByUsername(username);
  }
}
