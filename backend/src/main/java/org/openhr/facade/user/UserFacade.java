package org.openhr.facade.user;

import org.openhr.domain.user.User;

public interface UserFacade {
  User findByUsername(String username);

  void registerUser(User user);
}
