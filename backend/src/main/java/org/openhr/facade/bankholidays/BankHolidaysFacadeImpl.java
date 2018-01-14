package org.openhr.facade.bankholidays;

import org.openhr.domain.bankholidays.BankHolidays;
import org.openhr.service.bankholidays.BankHolidaysService;
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
