package org.openhr.security.exception;

public final class InvalidJWT extends Exception {
  public InvalidJWT() {}

  public InvalidJWT(final String message) {
    super(message);
  }
}
