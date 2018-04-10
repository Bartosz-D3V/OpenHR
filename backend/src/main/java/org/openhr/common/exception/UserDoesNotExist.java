package org.openhr.common.exception;

public class UserDoesNotExist extends Exception {
  /**
   * Exception thrown when requested user does not exist
   *
   * @param message Message to be thrown
   */
  public UserDoesNotExist(final String message) {
    super(message);
  }
}
