package org.openhr.api.bankholidays.service;

import org.openhr.api.bankholidays.domain.BankHolidays;
import org.openhr.api.bankholidays.repository.BankHolidaysRepository;
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
