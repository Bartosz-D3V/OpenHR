package org.openhr.repository.user;

import org.openhr.domain.user.User;

public interface UserRepository {
  User findByEmail(String email);

  void registerUser(User user);
}
