package org.openhr.api.bankholidays.domain;

import java.io.Serializable;
import java.util.Set;

public class BankHolidays implements Serializable {
  private String division;
  private Set<BankHoliday> events;

  public BankHolidays() {}

  public BankHolidays(final String division, final Set<BankHoliday> events) {
    this.division = division;
    this.events = events;
  }

  public String getDivision() {
    return division;
  }

  public void setDivision(final String division) {
    this.division = division;
  }

  public Set<BankHoliday> getEvents() {
    return events;
  }

  public void setEvents(final Set<BankHoliday> events) {
    this.events = events;
  }
}
