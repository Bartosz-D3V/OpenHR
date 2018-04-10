package org.openhr.common.exception;

public class ApplicationDoesNotExistException extends Exception {

  /** Exception occurring when application was not found using given ID */
  public ApplicationDoesNotExistException() {}

  /**
   * Exception occurring when application was not found using given ID
   *
   * @param message Message to be thrown
   */
  public ApplicationDoesNotExistException(final String message) {
    super(message);
  }
}
