package org.openhr.common.domain.subject;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.openhr.application.user.domain.User;
import org.openhr.common.enumeration.Role;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Subject implements Serializable {
  @Id
  @Column(name = "SUBJECT_ID")
  @GenericGenerator(
    name = "ID_GENERATOR",
    strategy = "enhanced-sequence",
    parameters = {
      @Parameter(name = "sequence_name", value = "SUBJECT_SEQUENCE_ID"),
      @Parameter(name = "initial_value", value = "2")
    }
  )
  @GeneratedValue(generator = "ID_GENERATOR")
  private long subjectId;

  @JoinColumn(unique = true, name = "PERSONAL_INFORMATION_ID")
  @OneToOne(
    fetch = FetchType.EAGER,
    optional = false,
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private PersonalInformation personalInformation;

  @JoinColumn(unique = true, name = "CONTACT_INFORMATION_ID")
  @OneToOne(
    fetch = FetchType.EAGER,
    optional = false,
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private ContactInformation contactInformation;

  @JoinColumn(unique = true, name = "EMPLOYEE_INFORMATION_ID")
  @OneToOne(
    fetch = FetchType.EAGER,
    optional = false,
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private EmployeeInformation employeeInformation;

  @JoinColumn(unique = true, name = "HR_INFORMATION_ID")
  @OneToOne(
    fetch = FetchType.EAGER,
    optional = false,
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private HrInformation hrInformation;

  @Enumerated(EnumType.STRING)
  private Role role;

  @JsonBackReference
  @OneToOne(
    fetch = FetchType.EAGER,
    orphanRemoval = true,
    cascade = CascadeType.ALL,
    optional = false
  )
  private User user;

  public Subject() {
    super();
  }

  public Subject(
      final PersonalInformation personalInformation,
      final ContactInformation contactInformation,
      final EmployeeInformation employeeInformation,
      final HrInformation hrInformation,
      final User user) {
    super();
    this.personalInformation = personalInformation;
    this.contactInformation = contactInformation;
    this.employeeInformation = employeeInformation;
    this.hrInformation = hrInformation;
    this.user = user;
  }

  public long getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(final long subjectId) {
    this.subjectId = subjectId;
  }

  public PersonalInformation getPersonalInformation() {
    return personalInformation;
  }

  public void setPersonalInformation(final PersonalInformation personalInformation) {
    this.personalInformation = personalInformation;
  }

  public ContactInformation getContactInformation() {
    return contactInformation;
  }

  public void setContactInformation(final ContactInformation contactInformation) {
    this.contactInformation = contactInformation;
  }

  public EmployeeInformation getEmployeeInformation() {
    return employeeInformation;
  }

  public void setEmployeeInformation(final EmployeeInformation employeeInformation) {
    this.employeeInformation = employeeInformation;
  }

  public HrInformation getHrInformation() {
    return hrInformation;
  }

  public void setHrInformation(final HrInformation hrInformation) {
    this.hrInformation = hrInformation;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(final Role role) {
    this.role = role;
  }

  public User getUser() {
    return user;
  }

  public void setUser(final User user) {
    this.user = user;
  }
}
