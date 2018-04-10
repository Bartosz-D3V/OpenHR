package org.openhr.common.domain.country;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Country implements Serializable {
  @Id
  @Column(name = "COUNTRY_ID", unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long countryId;

  @Column(name = "COUNTRY_NAME", unique = true, nullable = false)
  private String countryName;

  @Column(name = "FLAG_URL")
  private String flagUrl;

  public Country() {
    super();
  }

  public long getCountryId() {
    return countryId;
  }

  public void setCountryId(final long countryId) {
    this.countryId = countryId;
  }

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(final String countryName) {
    this.countryName = countryName;
  }

  public String getFlagUrl() {
    return flagUrl;
  }

  public void setFlagUrl(final String flagUrl) {
    this.flagUrl = flagUrl;
  }
}
