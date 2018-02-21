package org.openhr.common.domain.subject;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.openhr.application.user.domain.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Employee extends Subject implements Serializable {
  @JsonBackReference(value = "employee-manager")
  @ManyToOne(cascade = CascadeType.MERGE)
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
