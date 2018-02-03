package org.openhr.api.bankholidays.facade;

import org.openhr.api.bankholidays.domain.BankHolidays;
import org.openhr.api.bankholidays.service.BankHolidaysService;
import org.springframework.stereotype.Component;

@Component
public class BankHolidaysFacadeImpl implements BankHolidaysFacade {
  private final BankHolidaysService bankHolidaysService;

  public BankHolidaysFacadeImpl(final BankHolidaysService bankHolidaysService) {
    this.bankHolidaysService = bankHolidaysService;
  }

  @Override
  public BankHolidays getBankHolidays(final String country) {
    return bankHolidaysService.getBankHolidays(country.toLowerCase());
  }
}
