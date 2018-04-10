package org.openhr.application.manager.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.hr.domain.HrTeamMember;
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
public class Manager extends Subject implements Serializable {
  @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private Set<Employee> employees;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "FK_HR_TEAM_MEMBER")
  private HrTeamMember hrTeamMember;

  public Manager() {
    super();
  }

  public Manager(
      final PersonalInformation personalInformation,
      final ContactInformation contactInformation,
      final EmployeeInformation employeeInformation,
      final HrInformation hrInformation,
      final User user) {
    super(personalInformation, contactInformation, employeeInformation, hrInformation, user);
  }

  public Set<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(final Set<Employee> employees) {
    this.employees = employees;
  }

  public HrTeamMember getHrTeamMember() {
    return hrTeamMember;
  }

  public void setHrTeamMember(final HrTeamMember hrTeamMember) {
    this.hrTeamMember = hrTeamMember;
  }
}
