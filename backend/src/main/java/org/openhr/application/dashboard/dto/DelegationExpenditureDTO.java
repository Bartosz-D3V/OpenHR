package org.openhr.application.dashboard.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DelegationExpenditureDTO implements Serializable {
  private int year;
  private BigDecimal totalExpenditure;

  public DelegationExpenditureDTO() {
    super();
  }

  public DelegationExpenditureDTO(final int year, final BigDecimal totalExpenditure) {
    this.year = year;
    this.totalExpenditure = totalExpenditure;
  }

  public long getYear() {
    return year;
  }

  public void setYear(final int year) {
    this.year = year;
  }

  public BigDecimal getTotalExpenditure() {
    return totalExpenditure;
  }

  public void setTotalExpenditure(final BigDecimal totalExpenditure) {
    this.totalExpenditure = totalExpenditure;
  }
}
