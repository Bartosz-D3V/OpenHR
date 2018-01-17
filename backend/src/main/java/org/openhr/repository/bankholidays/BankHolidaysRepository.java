package org.openhr.repository.bankholidays;

import org.openhr.domain.bankholidays.BankHolidays;

public interface BankHolidaysRepository {

  BankHolidays getBankHolidays(String country);

}
