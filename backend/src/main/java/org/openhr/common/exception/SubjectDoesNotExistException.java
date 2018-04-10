package org.openhr.common.exception;

/**
 * Exception thrown when MANDATORY subject was not found in the database, although it must exist at
 * that point.
 */
public class SubjectDoesNotExistException extends Exception {

  /** Exception occurring when subject was not found using given ID */
  public SubjectDoesNotExistException() {}

  /**
   * Exception occurring when subject was not found using given ID
   *
   * @param message Message to be thrown
   */
  public SubjectDoesNotExistException(final String message) {
    super(message);
  }
}
