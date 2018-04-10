package org.openhr.api.bankholidays.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankHolidaysRepositoryTest {
  private final String englandAndWalesAPI =
      "https://www.gov.uk/bank-holidays/england-and-wales.json";

  @Autowired private BankHolidaysRepositoryImpl bankHolidaysRepository;

  @Test
  public void getApiByCountryShouldReturnEnglandAPI() {
    final String api = bankHolidaysRepository.getApiByCountry("england");

    assertEquals(englandAndWalesAPI, api);
  }

  @Test
  public void getApiByCountryShouldReturnWalesAPI() {
    final String api = bankHolidaysRepository.getApiByCountry("wales");

    assertEquals(englandAndWalesAPI, api);
  }

  @Test
  public void getApiByCountryShouldReturnNullIfNothingMatches() {
    final String api = bankHolidaysRepository.getApiByCountry("columbia");

    assertNull(api);
  }
}
