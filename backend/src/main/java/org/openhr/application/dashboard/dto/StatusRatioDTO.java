package org.openhr.application.dashboard.dto;

public class StatusRatioDTO {
  private long accepted;
  private long rejected;

  public StatusRatioDTO() {}

  public long getAccepted() {
    return accepted;
  }

  public void setAccepted(final long accepted) {
    this.accepted = accepted;
  }

  public long getRejected() {
    return rejected;
  }

  public void setRejected(final long rejected) {
    this.rejected = rejected;
  }
}
