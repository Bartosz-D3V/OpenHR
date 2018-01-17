package org.openhr.service.bankholidays;

import org.openhr.domain.bankholidays.BankHolidays;
import org.openhr.repository.bankholidays.BankHolidaysRepository;
import org.springframework.stereotype.Service;

@Service
public class BankHolidaysServiceImpl implements BankHolidaysService {
  private final BankHolidaysRepository bankHolidaysRepository;

  public BankHolidaysServiceImpl(final BankHolidaysRepository bankHolidaysRepository) {
    this.bankHolidaysRepository = bankHolidaysRepository;
  }

  @Override
  public BankHolidays getBankHolidays(final String country) {
    return bankHolidaysRepository.getBankHolidays(country);
  }
}
