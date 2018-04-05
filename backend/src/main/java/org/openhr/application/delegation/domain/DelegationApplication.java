package org.openhr.application.delegation.domain;

import org.openhr.common.domain.application.Application;
import org.openhr.common.domain.country.Country;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "DELEGATION_APPLICATION")
public class DelegationApplication extends Application implements Serializable {
  private Country country;
  private String city;
  private String objective;
  private BigDecimal budget;

  public DelegationApplication() {
    super();
  }

  public DelegationApplication(final LocalDate startDate, final LocalDate endDate) {
    super(startDate, endDate);
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(final Country country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(final String city) {
    this.city = city;
  }

  public String getObjective() {
    return objective;
  }

  public void setObjective(final String objective) {
    this.objective = objective;
  }

  public BigDecimal getBudget() {
    return budget;
  }

  public void setBudget(final BigDecimal budget) {
    this.budget = budget;
  }
}
