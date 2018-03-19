package org.openhr.application.user.service;

import org.openhr.application.user.domain.User;
import org.openhr.application.user.dto.PasswordDTO;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.common.exception.UserDoesNotExist;
import org.openhr.application.user.repository.UserRepository;
import org.openhr.application.authentication.service.AuthenticationService;
import org.openhr.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final AuthenticationService authenticationService;

  public UserServiceImpl(final UserRepository userRepository,
                         final AuthenticationService authenticationService) {
    this.userRepository = userRepository;
    this.authenticationService = authenticationService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public User getUserBySubjectId(final long subjectId) throws UserDoesNotExist {
    final User user = userRepository.getUserBySubjectId(subjectId);
    if (user == null) {
      throw new UserDoesNotExist("Requested user does not exist");
    }
    return user;
  }


  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public User getUserByUsername(final String username) throws UserDoesNotExist {
    final User user = userRepository.getUserByUsername(username);
    if (user == null) {
      throw new UserDoesNotExist("Requested user does not exist");
    }
    return user;
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public User updateUser(final long userId, final User user) {
    return userRepository.updateUser(userId, user);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void registerUser(final User user) throws UserAlreadyExists {
    final boolean isUsernameFree = isUsernameFree(user.getUsername());
    if (!isUsernameFree) {
      throw new UserAlreadyExists("Provided username is already in use");
    }
    user.setPassword(authenticationService.encodePassword(user.getPassword()));
    userRepository.registerUser(user);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updatePassword(final long subjectId, final PasswordDTO passwordDTO)
    throws UserDoesNotExist, ValidationException {
    final User user = getUserBySubjectId(subjectId);
    if (validCredentials(user.getUsername(), passwordDTO.getOldPassword())) {
      user.setPassword(authenticationService.encodePassword(passwordDTO.getNewPassword()));
      userRepository.updateUser(user.getUserId(), user);
    } else {
      throw new ValidationException("Provided password is not valid");
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public boolean validCredentials(final String username, final String password) {
    final User user = userRepository.getUserByUsername(username);
    return user != null && authenticationService.passwordsMatch(password, user.getPassword());
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public boolean isUsernameFree(final String username) {
    return !userRepository.retrieveUsernamesInUse().contains(username);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long findSubjectId(final String username) throws SubjectDoesNotExistException {
    return userRepository.findSubjectId(username);
  }
}
