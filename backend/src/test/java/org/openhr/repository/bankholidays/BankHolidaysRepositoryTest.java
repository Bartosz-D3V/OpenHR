package org.openhr.repository.bankholidays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankHolidaysRepositoryTest {
  private final String englandAndWalesAPI = "https://www.gov.uk/bank-holidays/england-and-wales.json";

  @MockBean
  private BankHolidaysRepositoryImpl bankHolidaysRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getApiByCountryShouldReturnEnglandAPI() {
    when(bankHolidaysRepository.getEnglandAndWalesAPI()).thenReturn(englandAndWalesAPI);
    final String api = bankHolidaysRepository.getApiByCountry("england");

    assertEquals(englandAndWalesAPI, api);
  }

  @Test
  public void getApiByCountryShouldReturnWalesAPI() {
    when(bankHolidaysRepository.getEnglandAndWalesAPI()).thenReturn(englandAndWalesAPI);
    final String api = bankHolidaysRepository.getApiByCountry("wales");

    assertEquals(englandAndWalesAPI, api);
  }

  @Test
  public void getApiByCountryShouldReturnNullIfNothingMatches() {
    final String api = bankHolidaysRepository.getApiByCountry("columbia");

    assertNull(api);
  }
}
