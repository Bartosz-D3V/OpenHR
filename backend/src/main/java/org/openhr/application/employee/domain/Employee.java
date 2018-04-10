package org.openhr.application.employee.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;

@Entity
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "subjectId",
  scope = Long.class
)
public class Employee extends Subject implements Serializable {
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "FK_MANAGER")
  private Manager manager;

  public Employee() {
    super();
  }

  public Employee(
      final PersonalInformation personalInformation,
      final ContactInformation contactInformation,
      final EmployeeInformation employeeInformation,
      final HrInformation hrInformation,
      final User user) {
    super(personalInformation, contactInformation, employeeInformation, hrInformation, user);
  }

  public Manager getManager() {
    return manager;
  }

  public void setManager(final Manager manager) {
    this.manager = manager;
  }
}
