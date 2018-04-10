package org.openhr.api.bankholidays.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class BankHoliday implements Serializable {
  private String title;
  private LocalDate date;
  private String notes;
  private boolean bunting;

  public BankHoliday() {}

  public BankHoliday(
      final String title, final LocalDate date, final String notes, final boolean bunting) {
    this.title = title;
    this.date = date;
    this.notes = notes;
    this.bunting = bunting;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(final LocalDate date) {
    this.date = date;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(final String notes) {
    this.notes = notes;
  }

  public boolean isBunting() {
    return bunting;
  }

  public void setBunting(final boolean bunting) {
    this.bunting = bunting;
  }
}
