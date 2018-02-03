package org.openhr.application.authentication.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public AuthenticationServiceImpl(final BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public String encodePassword(final String password) {
    String encodedPassword = null;
    int i = 0;
    while (i < 12) {
      encodedPassword = bCryptPasswordEncoder.encode(password);
      ++i;
    }
    return encodedPassword;
  }

  @Override
  public boolean passwordsMatch(final String rawPassword, final String encodedPassword) {
    return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
  }

}
