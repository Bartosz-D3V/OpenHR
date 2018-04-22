package org.openhr.api.bankholidays.service;

import org.openhr.api.bankholidays.domain.BankHolidays;

public interface BankHolidaysService {

  BankHolidays getBankHolidays(String country);
}
