package org.openhr.application.user.dao;

import org.openhr.application.user.domain.User;

public interface UserDAO {
  User getUser(long userId);

  void registerUser(User user);

  User updateUser(long userId, User user);
}
