package org.openhr.application.user.repository;

import org.openhr.application.user.domain.User;

import java.util.List;

public interface UserRepository {
  User findByUsername(String username);

  long findUserId(String username);

  void registerUser(User user);

  String getEncodedPassword(long userId);

  List<String> retrieveUsernamesInUse();
}
