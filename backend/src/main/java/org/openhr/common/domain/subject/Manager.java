package org.openhr.common.domain.subject;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.openhr.application.user.domain.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Manager extends Subject implements Serializable {
  @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @NotFound(action = NotFoundAction.IGNORE)
  private Set<Employee> employees;

  public Manager() {
    super();
  }

  public Manager(final String firstName, final String lastName, final PersonalInformation personalInformation,
                 final ContactInformation contactInformation, final EmployeeInformation employeeInformation,
                 final HrInformation hrInformation, final User user) {
    super(firstName, lastName, personalInformation, contactInformation, employeeInformation, hrInformation, user);
  }

  public Set<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(final Set<Employee> employees) {
    this.employees = employees;
  }
}
