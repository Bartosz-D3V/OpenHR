package org.openhr.service.user;

import org.openhr.domain.user.User;

public interface UserService {
  User findByEmail(String email);

  void registerUser(User user);
}
