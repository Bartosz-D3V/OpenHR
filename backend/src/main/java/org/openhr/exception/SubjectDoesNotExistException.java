package org.openhr.exception;

/**
 * Exception thrown when required subject was not found in the
 * database, although it must exist at that point.
 */
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
