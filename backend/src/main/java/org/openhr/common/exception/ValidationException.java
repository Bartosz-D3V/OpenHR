package org.openhr.common.exception;

/** Exception that shall be used during validation business rules */
public class ValidationException extends Exception {
  public ValidationException(final String message) {
    super(message);
  }
}
