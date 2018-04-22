package org.openhr.application.hr.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;

@Entity
@Table(name = "HR_TEAM_MEMBER")
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class,
  property = "subjectId",
  scope = Long.class
)
public class HrTeamMember extends Subject implements Serializable {
  @OneToMany(mappedBy = "hrTeamMember", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private Set<Manager> managers;

  public HrTeamMember() {
    super();
  }

  public HrTeamMember(
      final PersonalInformation personalInformation,
      final ContactInformation contactInformation,
      final EmployeeInformation employeeInformation,
      final HrInformation hrInformation,
      final User user) {
    super(personalInformation, contactInformation, employeeInformation, hrInformation, user);
  }

  public Set<Manager> getManagers() {
    return managers;
  }

  public void setManagers(final Set<Manager> managers) {
    this.managers = managers;
  }
}
