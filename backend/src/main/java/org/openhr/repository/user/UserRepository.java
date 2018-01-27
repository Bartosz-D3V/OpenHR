package org.openhr.repository.user;

import org.openhr.domain.user.User;

import java.util.List;

public interface UserRepository {
  User findByUsername(String username);

  long findUserId(String username);

  void registerUser(User user);

  String getEncodedPassword(long userId);

  List<String> retrieveUsernamesInUse();
}
