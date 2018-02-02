package org.openhr.service.authentication;

public interface AuthenticationService {
  String encodePassword(String password);

  boolean passwordsMatch(String rawPassword, String encodedPassword);
}
