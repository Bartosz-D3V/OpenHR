package org.openhr.security;

import java.io.Serializable;

public final class SecurityConfigConstants implements Serializable {
  public static final long EXPIRATION_TIME_IN_MINS = 20L;
  public static final long LONG_EXPIRATION_TIME_IN_MINS = 40L;
  public static final long EXPIRATION_TIME_IN_MILIS = EXPIRATION_TIME_IN_MINS * 60000;
  public static final String TOKEN_PREFIX = "Bearer-";
  public static final String HEADER_STRING = "Authorization";
  public static final String SECRET = "%^&*SEC^R3T_T0K3N!";
}
