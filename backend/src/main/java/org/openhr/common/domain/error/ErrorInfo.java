package org.openhr.common.domain.error;

public class ErrorInfo {

  private final String url;
  private final String message;

  public ErrorInfo(final String url, final Exception ex) {
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
