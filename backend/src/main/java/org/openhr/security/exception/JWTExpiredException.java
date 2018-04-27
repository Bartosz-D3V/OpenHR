package org.openhr.security.exception;

public final class JWTExpiredException extends Exception {
  public JWTExpiredException() {}

  public JWTExpiredException(final String message) {
    super(message);
  }
}
