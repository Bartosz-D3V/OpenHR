package org.openhr.common.domain.address;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class Address implements Serializable {
  @Column(name = "FIRST_LINE_ADDRESS")
  @Size(max = 80)
  private String firstLineAddress;

  @Column(name = "SECOND_LINE_ADDRESS")
  @Size(max = 80)
  private String secondLineAddress;

  @Column(name = "THIRD_LINE_ADDRESS")
  @Size(max = 80)
  private String thirdLineAddress;

  @Size(max = 10)
  private String postcode;

  @Size(max = 50)
  private String city;

  @Size(max = 50)
  private String country;

  public Address() {
    super();
  }

  public Address(
      final String firstLineAddress,
      final String secondLineAddress,
      final String thirdLineAddress,
      final String postcode,
      final String city,
      String country) {
    this.firstLineAddress = firstLineAddress;
    this.secondLineAddress = secondLineAddress;
    this.thirdLineAddress = thirdLineAddress;
    this.postcode = postcode;
    this.city = city;
    this.country = country;
  }

  public String getFirstLineAddress() {
    return firstLineAddress;
  }

  public void setFirstLineAddress(final String firstLineAddress) {
    this.firstLineAddress = firstLineAddress;
  }

  public String getSecondLineAddress() {
    return secondLineAddress;
  }

  public void setSecondLineAddress(final String secondLineAddress) {
    this.secondLineAddress = secondLineAddress;
  }

  public String getThirdLineAddress() {
    return thirdLineAddress;
  }

  public void setThirdLineAddress(final String thirdLineAddress) {
    this.thirdLineAddress = thirdLineAddress;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(final String postcode) {
    this.postcode = postcode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(final String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(final String country) {
    this.country = country;
  }
}
