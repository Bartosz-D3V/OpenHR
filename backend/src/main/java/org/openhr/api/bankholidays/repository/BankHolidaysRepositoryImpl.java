package org.openhr.api.bankholidays.repository;

import org.openhr.api.bankholidays.domain.BankHolidays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class BankHolidaysRepositoryImpl implements BankHolidaysRepository {
  @Value(value = "${bankHolidaysAPI.englandAndWales}")
  private String englandAndWalesAPI;

  @Override
  public BankHolidays getBankHolidays(final String country) {
    final RestTemplate restTemplate = new RestTemplate();
    final String apiAddress = getApiByCountry(country);
    return restTemplate.getForObject(apiAddress, BankHolidays.class);
  }

  String getApiByCountry(final String country) {
    switch (country.toLowerCase()) {
      case "england":
      case "wales":
        return englandAndWalesAPI;
    }
    return null;
  }
}
