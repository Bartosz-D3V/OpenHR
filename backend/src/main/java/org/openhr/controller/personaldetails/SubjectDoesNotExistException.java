package org.openhr.controller.personaldetails;

public class SubjectDoesNotExistException extends Exception {

  /**
   * Exception occurring when subject was not found using
   * given ID
   */
  public SubjectDoesNotExistException() {
  }

  /**
   * Exception occurring when subject was not found using
   * given ID
   *
   * @param message: message to be thrown and logged
   */
  public SubjectDoesNotExistException(final String message) {
    super(message);
  }
}
