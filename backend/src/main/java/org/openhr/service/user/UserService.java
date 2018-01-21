package org.openhr.service.user;

import org.openhr.domain.authority.Authority;
import org.openhr.domain.user.User;

import java.util.List;

public interface UserService {
  User findByUsername(String username);

  void registerUser(User user);

  String getEncodedPassword(long userId);

  String getEncodedPassword(String username);

  boolean validCredentials(String username, String password);

  List<Authority> getGrantedAuthorities(String username);
}
