package org.openhr.common.domain.subject;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.user.domain.User;

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
  @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
  private PersonalInformation personalInformation;

  @JoinColumn(unique = true, name = "CONTACT_INFORMATION_ID")
  @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
  private ContactInformation contactInformation;

  @JoinColumn(unique = true, name = "EMPLOYEE_INFORMATION_ID")
  @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
  private EmployeeInformation employeeInformation;

  @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<LeaveApplication> leaveApplications = new HashSet<>();

  @JoinColumn(unique = true, name = "EMPLOYEE_ID")
  @NotFound(action = NotFoundAction.IGNORE)
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Employee employee;

  @JoinColumn(unique = true, name = "MANAGER_ID")
  @NotFound(action = NotFoundAction.IGNORE)
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Manager manager;

  @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
  private User user;

  public Subject() {
    super();
  }

  public Subject(final String firstName, final String lastName, final User user) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.user = user;
  }

  public Subject(final String firstName, final String lastName, final PersonalInformation personalInformation,
                 final ContactInformation contactInformation, final EmployeeInformation employeeInformation,
                 final User user) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.personalInformation = personalInformation;
    this.contactInformation = contactInformation;
    this.employeeInformation = employeeInformation;
    this.user = user;
  }

  public long getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(final long subjectId) {
    this.subjectId = subjectId;
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

  public User getUser() {
    return user;
  }

  public void setUser(final User user) {
    this.user = user;
  }
}
