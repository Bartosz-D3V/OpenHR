package org.openhr.application.leaveapplication.domain;

import org.openhr.common.domain.application.Application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "LEAVE_APPLICATION")
public class LeaveApplication extends Application implements Serializable {

  @Size(max = 500, message = "Message cannot be greater than {max} characters long")
  @Column(length = 500)
  private String message;

  @NotNull(message = "Leave type cannot be empty")
  @JoinColumn(name = "LEAVE_TYPE_LEAVE_TYPE_ID")
  @ManyToOne(optional = false)
  private LeaveType leaveType;

  public LeaveApplication() {
    super();
  }

  public LeaveApplication(final LocalDate startDate, final LocalDate endDate) {
    super(startDate, endDate);
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public LeaveType getLeaveType() {
    return leaveType;
  }

  public void setLeaveType(final LeaveType leaveType) {
    this.leaveType = leaveType;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof LeaveApplication)) return false;
    final LeaveApplication that = (LeaveApplication) o;
    return Objects.equals(getMessage(), that.getMessage()) &&
      Objects.equals(getLeaveType(), that.getLeaveType());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getMessage(), getLeaveType());
  }
}
