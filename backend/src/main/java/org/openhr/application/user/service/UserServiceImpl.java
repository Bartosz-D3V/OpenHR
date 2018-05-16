package org.openhr.application.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.domain.UserRole;
import org.openhr.application.user.dto.PasswordDTO;
import org.openhr.application.user.repository.UserRepository;
import org.openhr.common.enumeration.Role;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.common.exception.UserDoesNotExist;
import org.openhr.common.exception.ValidationException;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final MessageSource messageSource;

  public UserServiceImpl(
      final UserRepository userRepository,
      final BCryptPasswordEncoder bCryptPasswordEncoder,
      final MessageSource messageSource) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.messageSource = messageSource;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public User getUser(final long userId) {
    return userRepository.getUser(userId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public User getUserByUsername(final String username) throws UserDoesNotExist {
    final User user = userRepository.getUserByUsername(username);
    if (user == null) {
      throw new UserDoesNotExist(
          messageSource.getMessage("error.userdoesnotexist", null, Locale.getDefault()));
    }
    return user;
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public User updateUser(final long userId, final User user) {
    return userRepository.updateUser(userId, user);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void registerUser(final User user) throws UserAlreadyExists {
    final boolean isUsernameFree = isUsernameFree(user.getUsername());
    if (!isUsernameFree) {
      throw new UserAlreadyExists(
          messageSource.getMessage("error.useralreadyexist", null, Locale.getDefault()));
    }
    user.setPassword(encodePassword(user.getPassword()));
    userRepository.registerUser(user);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public User getUserBySubjectId(final long subjectId) {
    return userRepository.getUserBySubjectId(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updateNotificationsSettings(final long userId, final boolean notificationsTurnedOn) {
    userRepository.updateNotificationsSettings(userId, notificationsTurnedOn);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updatePassword(final long userId, final PasswordDTO passwordDTO)
      throws ValidationException, SubjectDoesNotExistException {
    final User user = getUser(userId);
    if (validCredentials(user.getUsername(), passwordDTO.getOldPassword())) {
      user.setPassword(encodePassword(passwordDTO.getNewPassword()));
      userRepository.updateUser(user.getUserId(), user);
    } else {
      throw new ValidationException(
          messageSource.getMessage("error.validation.passwordnotvalid", null, Locale.getDefault()));
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public boolean validCredentials(final String username, final String password) {
    final User user = userRepository.getUserByUsername(username);
    return user != null && passwordsMatch(password, user.getPassword());
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

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public boolean notificationsEnabled(final long userId) {
    return getUser(userId).isNotificationsTurnedOn();
  }

  @Override
  public final String encodePassword(final String password) {
    String encodedPassword = null;
    int i = 0;
    while (i < 12) {
      encodedPassword = bCryptPasswordEncoder.encode(password);
      ++i;
    }
    return encodedPassword;
  }

  @Override
  public boolean passwordsMatch(final String rawPassword, final String encodedPassword) {
    return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
  }

  @Override
  public List<UserRole> setBasicUserRoles(final User user) {
    final List<UserRole> basicUserRoles = new ArrayList<>();
    final UserRole userRole = new UserRole(Role.EMPLOYEE);
    userRole.setUser(user);
    basicUserRoles.add(userRole);
    return basicUserRoles;
  }

  @Override
  public List<UserRole> setManagerUserRole(final User user) {
    final List<UserRole> managerUserRoles = new ArrayList<>();
    final UserRole userRole = new UserRole(Role.MANAGER);
    userRole.setUser(user);
    managerUserRoles.add(userRole);
    return managerUserRoles;
  }

  @Override
  public List<UserRole> setHrUserRole(final User user) {
    final List<UserRole> hrUserRoles = new ArrayList<>();
    final UserRole userRole = new UserRole(Role.HRTEAMMEMBER);
    userRole.setUser(user);
    hrUserRoles.add(userRole);
    return hrUserRoles;
  }
}
