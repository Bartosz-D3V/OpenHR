package org.openhr.application.leaveapplication.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "LEAVE_TYPE")
public class LeaveType implements Serializable {
  @Id
  @Column(name = "LEAVE_TYPE_ID", unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long leaveTypeId;

  @Column(name = "LEAVE_CATEGORY")
  @Size(max = 150)
  @NotNull
  private String leaveCategory;

  @Size(max = 200)
  private String description;

  public LeaveType() {
    super();
  }

  public LeaveType(final String leaveCategory, final String description) {
    this.leaveCategory = leaveCategory;
    this.description = description;
  }

  public long getLeaveTypeId() {
    return leaveTypeId;
  }

  public void setLeaveTypeId(final long leaveTypeId) {
    this.leaveTypeId = leaveTypeId;
  }

  public String getLeaveCategory() {
    return leaveCategory;
  }

  public void setLeaveCategory(final String leaveCategory) {
    this.leaveCategory = leaveCategory;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof LeaveType)) return false;
    final LeaveType leaveType = (LeaveType) o;
    return getLeaveTypeId() == leaveType.getLeaveTypeId() &&
      Objects.equals(getLeaveCategory(), leaveType.getLeaveCategory()) &&
      Objects.equals(getDescription(), leaveType.getDescription());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getLeaveTypeId(), getLeaveCategory(), getDescription());
  }

  @Override
  public String toString() {
    return "LeaveType{" +
      "leaveTypeId=" + leaveTypeId +
      ", leaveCategory='" + leaveCategory + '\'' +
      ", description='" + description + '\'' +
      '}';
  }
}
