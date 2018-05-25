package org.openhr.application.adminconfiguration.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ALLOWANCE_SETTINGS")
public final class AllowanceSettings implements Serializable {
  @Id private long id = 1;

  @Column(name = "AUTO_RESET_ALLOWANCE")
  private boolean autoResetAllowance = false;

  @Column(name = "RESET_DATE")
  private LocalDate resetDate;

  @Column(name = "CARRY_OVER_UNUSED_LEAVE")
  private boolean carryOverUnusedLeave = false;

  @Column(name = "NUMBER_OF_DAYS_TO_CARRY_OVER")
  private int numberOfDaysToCarryOver = 0;

  public AllowanceSettings() {
    super();
  }

  public boolean isAutoResetAllowance() {
    return autoResetAllowance;
  }

  public void setAutoResetAllowance(final boolean autoResetAllowance) {
    this.autoResetAllowance = autoResetAllowance;
  }

  public LocalDate getResetDate() {
    return resetDate;
  }

  public void setResetDate(final LocalDate resetDate) {
    this.resetDate = resetDate;
  }

  public boolean isCarryOverUnusedLeave() {
    return carryOverUnusedLeave;
  }

  public void setCarryOverUnusedLeave(final boolean carryOverUnusedLeave) {
    this.carryOverUnusedLeave = carryOverUnusedLeave;
  }

  public int getNumberOfDaysToCarryOver() {
    return numberOfDaysToCarryOver;
  }

  public void setNumberOfDaysToCarryOver(final int numberOfDaysToCarryOver) {
    this.numberOfDaysToCarryOver = numberOfDaysToCarryOver;
  }
}
