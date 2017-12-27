package org.openhr.domain.subject;

import org.openhr.domain.application.LeaveApplication;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Subject implements Serializable {
  @Id
  @Column(name = "SUBJECT_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long subjectId;

  @NotNull
  @Column(name = "FIRST_NAME")
  private String firstName;

  @NotNull
  @Column(name = "LAST_NAME")
  private String lastName;

  @JoinColumn(unique = true, name = "PERSONAL_INFORMATION_ID")
  @OneToOne(fetch = FetchType.EAGER, optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
  private PersonalInformation personalInformation;

  @JoinColumn(unique = true, name = "CONTACT_INFORMATION_ID")
  @OneToOne(fetch = FetchType.EAGER, optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
  private ContactInformation contactInformation;

  @JoinColumn(unique = true, name = "EMPLOYEE_INFORMATION_ID")
  @OneToOne(fetch = FetchType.EAGER, optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
  private EmployeeInformation employeeInformation;

  @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<LeaveApplication> leaveApplications = new HashSet<>();

  public Subject() {
    super();
  }

  public Subject(final String firstName, final String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;

  }

  public Subject(final String firstName, final String lastName, final PersonalInformation personalInformation,
                 final ContactInformation contactInformation, final EmployeeInformation employeeInformation) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.personalInformation = personalInformation;
    this.contactInformation = contactInformation;
    this.employeeInformation = employeeInformation;
  }

  public long getSubjectId() {
    return subjectId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
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

  public Set<LeaveApplication> getLeaveApplications() {
    return leaveApplications;
  }

  public void setLeaveApplications(final Set<LeaveApplication> leaveApplications) {
    this.leaveApplications = leaveApplications;
  }

  public void addLeaveApplication(final LeaveApplication leaveApplication) {
    this.leaveApplications.add(leaveApplication);
  }
}
