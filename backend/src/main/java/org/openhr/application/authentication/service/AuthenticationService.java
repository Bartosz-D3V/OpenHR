package org.openhr.application.authentication.service;

public interface AuthenticationService {
  String encodePassword(String password);

  boolean passwordsMatch(String rawPassword, String encodedPassword);
}
