package org.openhr.domain.address;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Address {

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

  public Address(String firstLineAddress, String secondLineAddress, String thirdLineAddress, String postcode,
                 String city, String country) {
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

  public void setFirstLineAddress(String firstLineAddress) {
    this.firstLineAddress = firstLineAddress;
  }

  public String getSecondLineAddress() {
    return secondLineAddress;
  }

  public void setSecondLineAddress(String secondLineAddress) {
    this.secondLineAddress = secondLineAddress;
  }

  public String getThirdLineAddress() {
    return thirdLineAddress;
  }

  public void setThirdLineAddress(String thirdLineAddress) {
    this.thirdLineAddress = thirdLineAddress;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
}
