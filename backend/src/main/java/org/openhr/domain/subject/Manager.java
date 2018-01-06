package org.openhr.domain.subject;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Manager implements Serializable {
  @Id
  @Column(name = "MANAGER_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long managerId;

  @OneToOne(mappedBy = "manager")
  @PrimaryKeyJoinColumn
  @NotFound(action = NotFoundAction.IGNORE)
  private Subject subject;

  @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @NotFound(action = NotFoundAction.IGNORE)
  private Set<Employee> employees;

  public Manager() {
    super();
  }

  public Manager(final Subject subject) {
    this.subject = subject;
  }

  public long getManagerId() {
    return managerId;
  }

  public void setManagerId(final long managerId) {
    this.managerId = managerId;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(final Subject subject) {
    this.subject = subject;
  }

  public Set<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(final Set<Employee> employees) {
    this.employees = employees;
  }
}
