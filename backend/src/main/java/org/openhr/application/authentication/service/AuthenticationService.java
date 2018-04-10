package org.openhr.application.authentication.service;

import java.util.List;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.domain.UserRole;

public interface AuthenticationService {
  String encodePassword(String password);

  boolean passwordsMatch(String rawPassword, String encodedPassword);

  List<UserRole> setBasicUserRoles(User user);

  List<UserRole> setManagerUserRole(User user);

  List<UserRole> setHrUserRole(User user);
}
