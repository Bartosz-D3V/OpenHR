package org.openhr.common.domain.subject;

import org.openhr.application.user.domain.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Employee extends Subject implements Serializable {
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "MANAGER_ID")
  private Manager manager;

  public Employee() {
    super();
  }

  public Employee(final String firstName, final String lastName, final PersonalInformation personalInformation,
                  final ContactInformation contactInformation, final EmployeeInformation employeeInformation,
                  final HrInformation hrInformation, final User user) {
    super(firstName, lastName, personalInformation, contactInformation, employeeInformation, hrInformation, user);
  }

  public Manager getManager() {
    return manager;
  }

  public void setManager(final Manager manager) {
    this.manager = manager;
  }
}
