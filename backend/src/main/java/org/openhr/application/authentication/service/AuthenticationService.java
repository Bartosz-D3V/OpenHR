package org.openhr.application.authentication.service;

import org.openhr.application.user.domain.User;
import org.openhr.application.user.domain.UserRole;

import java.util.List;

public interface AuthenticationService {
  String encodePassword(String password);

  boolean passwordsMatch(String rawPassword, String encodedPassword);

  List<UserRole> setBasicUserRoles(User user);
}
