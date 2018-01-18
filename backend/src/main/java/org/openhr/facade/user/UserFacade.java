package org.openhr.facade.user;

import org.openhr.domain.user.User;

public interface UserFacade {
  User findByEmail(String email);

  User findByUsername(String username);

  void registerUser(User user);
}
