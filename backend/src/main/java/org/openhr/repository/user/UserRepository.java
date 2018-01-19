package org.openhr.repository.user;

import org.openhr.domain.user.User;

public interface UserRepository {
  User findByUsername(String username);

  void registerUser(User user);

  String getEncodedPassword(String username);
}
