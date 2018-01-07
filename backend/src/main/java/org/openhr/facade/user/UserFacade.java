package org.openhr.facade.user;

import org.openhr.domain.user.User;

public interface UserFacade {
  User findByEmail(String email);

  void registerUser(User user);
}
