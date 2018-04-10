package org.openhr.common.exception;

public class UserAlreadyExists extends Exception {
  /**
   * Exception thrown when user tries to register another user with the same username
   *
   * @param message Message to be thrown
   */
  public UserAlreadyExists(final String message) {
    super(message);
  }
}
