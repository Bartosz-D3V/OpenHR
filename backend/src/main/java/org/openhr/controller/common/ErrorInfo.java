package org.openhr.controller.common;

public class ErrorInfo {

  private final String url;
  private final String message;

  public ErrorInfo(String url, Exception ex) {
    this.url = url;
    this.message = ex.getLocalizedMessage();
  }

  public String getUrl() {
    return url;
  }

  public String getMessage() {
    return message;
  }
}