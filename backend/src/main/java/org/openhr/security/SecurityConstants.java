package org.openhr.security;

import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

class SecurityConstants implements Serializable {
  private final static long EXPIRATION_TIME_IN_MINS = 15L;
  final static long EXPIRATION_TIME_IN_MILIS = EXPIRATION_TIME_IN_MINS * 60000;
  final static String TOKEN_PREFIX = "Bearer ";
  final static String HEADER_STRING = "Authorization";

  @Value("${jwt.secret}")
  static String SECRET;
}
