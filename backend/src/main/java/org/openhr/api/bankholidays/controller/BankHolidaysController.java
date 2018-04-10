package org.openhr.api.bankholidays.controller;

import org.openhr.api.bankholidays.domain.BankHolidays;
import org.openhr.api.bankholidays.facade.BankHolidaysFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bank-holidays")
public class BankHolidaysController {
  private final BankHolidaysFacade bankHolidaysFacade;

  public BankHolidaysController(final BankHolidaysFacade bankHolidaysFacade) {
    this.bankHolidaysFacade = bankHolidaysFacade;
  }

  @RequestMapping(
    value = "/{country}",
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  public BankHolidays getBankHolidays(@PathVariable(name = "country") final String country) {
    return bankHolidaysFacade.getBankHolidays(country);
  }
}
