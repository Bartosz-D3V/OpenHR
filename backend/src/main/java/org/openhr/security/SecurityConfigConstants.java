package org.openhr.security;

import java.io.Serializable;

public final class SecurityConfigConstants implements Serializable {
  public final static long EXPIRATION_TIME_IN_MINS = 20L;
  public final static long EXPIRATION_TIME_IN_MILIS = EXPIRATION_TIME_IN_MINS * 60000;
  public final static String TOKEN_PREFIX = "Bearer-";
  public final static String HEADER_STRING = "Authorization";
  public final static String SECRET = "%^&*SEC^R3T_T0K3N!";
}
