package org.openhr.api.bankholidays.repository;

import org.openhr.api.bankholidays.domain.BankHolidays;

public interface BankHolidaysRepository {

  BankHolidays getBankHolidays(String country);
}
