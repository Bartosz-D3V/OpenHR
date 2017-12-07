package org.openhr.exception;

public class ApplicationDoesNotExistException extends Exception {

  /**
   * Exception occurring when application was not found using
   * given ID
   */
  public ApplicationDoesNotExistException() {
  }

  /**
   * Exception occurring when application was not found using
   * given ID
   *
   * @param message: message to be thrown and logged
   */
  public ApplicationDoesNotExistException(final String message) {
    super(message);
  }

}
