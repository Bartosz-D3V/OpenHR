package org.openhr.api.bankholidays.facade;

import org.openhr.api.bankholidays.domain.BankHolidays;

public interface BankHolidaysFacade {

  BankHolidays getBankHolidays(String country);
}
