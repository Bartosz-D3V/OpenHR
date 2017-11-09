package org.openhr.domain.address;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class Address implements Serializable {

  @NotNull
  @NotEmpty
  @Column(name = "first_line_address")
  private String firstLineAddress;

  @Column(name = "second_line_address")
  private String secondLineAddress;

  @Column(name = "third_line_address")
  private String thirdLineAddress;

  @NotNull
  @NotEmpty
  private String postcode;

  private String city;
  private String country;

  public Address() {
  }

  public Address(final String firstLineAddress, final String secondLineAddress, final String thirdLineAddress,
                 final String postcode, final String city, String country) {
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
