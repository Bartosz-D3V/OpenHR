package org.openhr.domain.subject;

import org.openhr.domain.address.Address;

import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Table(name = "CONTACT_INFORMATION")
public class ContactInformation implements Serializable {
  @NotNull
  @OneToOne
  @JoinColumn(name = "SUBJECT_ID")
  private Subject subject;
  private String telephone;
  private String email;

  @Embedded
  private Address address;

  public ContactInformation() {
  }

  public ContactInformation(final Subject subject, final String telephone, final String email, final Address address) {
    this.subject = subject;
    this.telephone = telephone;
    this.email = email;
    this.address = address;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(final Subject subject) {
    this.subject = subject;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(final String telephone) {
    this.telephone = telephone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(final Address address) {
    this.address = address;
  }
}
