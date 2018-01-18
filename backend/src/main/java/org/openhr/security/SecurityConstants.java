package org.openhr.security;

import java.io.Serializable;

class SecurityConstants implements Serializable {
  private final static long EXPIRATION_TIME_IN_MINS = 15L;
  final static long EXPIRATION_TIME_IN_MILIS = EXPIRATION_TIME_IN_MINS * 60000;
  final static String TOKEN_PREFIX = "Bearer ";
  final static String HEADER_STRING = "Authorization";
  final static String SECRET = "%^&*SEC^R3T_T0K3N!";
}
