package org.openhr.common.domain.subject;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.user.domain.User;
import org.openhr.common.enumeration.Role;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Subject implements Serializable {
  @Id
  @Column(name = "SUBJECT_ID")
  @GenericGenerator(
    name = "ID_GENERATOR",
    strategy = "enhanced-sequence",
    parameters = {
      @Parameter(
        name = "sequence_name",
        value = "JPWH_SEQUENCE"
      ),
      @Parameter(
        name = "initial_value",
        value = "1"
      )
    }
  )
  @GeneratedValue(generator = "ID_GENERATOR")
  private long subjectId;

  @NotNull
  @Column(name = "FIRST_NAME")
  @Size(max = 255)
  private String firstName;

  @NotNull
  @Column(name = "LAST_NAME")
  @Size(max = 255)
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

  @JoinColumn(unique = true, name = "HR_INFORMATION_ID")
  @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
  private HrInformation hrInformation;

  @JsonIgnore
  @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<LeaveApplication> leaveApplications = new HashSet<>();

  @JsonIgnore
  @Enumerated(EnumType.STRING)
  private Role role;

  @JsonBackReference
  @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, optional = false)
  private User user;

  public Subject() {
    super();
  }

  public Subject(final String firstName, final String lastName, final PersonalInformation personalInformation,
                 final ContactInformation contactInformation, final EmployeeInformation employeeInformation,
                 final HrInformation hrInformation, final User user) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
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

  public void addLeaveApplication(final LeaveApplication leaveApplication) {
    this.leaveApplications.add(leaveApplication);
  }
}
