package org.openhr.common.domain.subject;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.openhr.application.user.domain.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "subjectId",
  scope = Long.class)
public class Employee extends Subject implements Serializable {
  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "FK_MANAGER")
  private Manager manager;

  public Employee() {
    super();
  }

  public Employee(final PersonalInformation personalInformation, final ContactInformation contactInformation,
                  final EmployeeInformation employeeInformation, final HrInformation hrInformation, final User user) {
    super(personalInformation, contactInformation, employeeInformation, hrInformation, user);
  }

  public Manager getManager() {
    return manager;
  }

  public void setManager(final Manager manager) {
    this.manager = manager;
  }
}
