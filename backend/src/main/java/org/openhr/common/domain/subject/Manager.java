package org.openhr.common.domain.subject;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.openhr.application.user.domain.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Manager extends Subject implements Serializable {
  @JsonManagedReference(value = "employee-manager")
  @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private Set<Employee> employees;

  public Manager() {
    super();
  }

  public Manager(final PersonalInformation personalInformation, final ContactInformation contactInformation,
                 final EmployeeInformation employeeInformation, final HrInformation hrInformation, final User user) {
    super(personalInformation, contactInformation, employeeInformation, hrInformation, user);
  }

  public Set<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(final Set<Employee> employees) {
    this.employees = employees;
  }
}
