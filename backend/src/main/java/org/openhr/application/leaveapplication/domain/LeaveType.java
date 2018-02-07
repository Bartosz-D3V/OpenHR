package org.openhr.application.leaveapplication.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "LEAVE_TYPE")
public class LeaveType implements Serializable {
  @Id
  @Column(name = "LEAVE_TYPE_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long leaveTypeId;

  private String type;
  private String description;

  public LeaveType() {
    super();
  }

  public LeaveType(final String type, final String description) {
    this.type = type;
    this.description = description;
  }

  public long getLeaveTypeId() {
    return leaveTypeId;
  }

  public void setLeaveTypeId(final long leaveTypeId) {
    this.leaveTypeId = leaveTypeId;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }
}
