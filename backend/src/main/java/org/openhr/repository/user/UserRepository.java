package org.openhr.repository.user;

import org.openhr.domain.user.User;

public interface UserRepository {
  User findByUsername(String username);

  long findUserId(String username);

  void registerUser(User user);

  String getEncodedPassword(long userId);

  boolean usernameIsFree(String username);
}
