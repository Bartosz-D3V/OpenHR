package org.openhr.common.domain.subject;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.openhr.common.domain.address.Address;

@Entity
@Table(name = "CONTACT_INFORMATION")
public class ContactInformation implements Serializable {
  @Id
  @Column(name = "CONTACT_INFORMATION_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long contactInformationId;

  @Size(max = 15)
  private String telephone;

  @Size(max = 40)
  private String email;

  @Embedded private Address address;

  public ContactInformation() {
    super();
  }

  public ContactInformation(final String telephone, final String email, final Address address) {
    this.telephone = telephone;
    this.email = email;
    this.address = address;
  }

  public long getContactInformationId() {
    return contactInformationId;
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
