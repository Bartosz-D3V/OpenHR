package org.openhr.application.authentication.service;

import org.openhr.application.user.domain.User;
import org.openhr.application.user.domain.UserRole;
import org.openhr.common.enumeration.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public AuthenticationServiceImpl(final BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
