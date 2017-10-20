package org.openhr.domain.error;

public class ErrorInfo {

  private final String url;
  private final String message;

  public ErrorInfo(String url, Exception ex) {
    this.url = url;
    this.message = ex.getLocalizedMessage();
  }

}