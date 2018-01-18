package org.openhr.service.user;

import org.openhr.domain.user.User;

public interface UserService {
  User findByEmail(String email);

  User findByUsername(String username);

  void registerUser(User user);

  String getEncodedPassword(String username);

  boolean validCredentials(String username, String password);
}
