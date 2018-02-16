package org.openhr.common.domain.subject;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Employee implements Serializable {
  @Id
  @Column(name = "EMPLOYEE_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long employeeId;

  @JsonBackReference
  @OneToOne(mappedBy = "employee")
  @PrimaryKeyJoinColumn
  @NotFound(action = NotFoundAction.IGNORE)
  private Subject subject;

  @NotNull
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "MANAGER_ID", nullable = false)
  private Manager manager;

  public Employee() {
    super();
  }

  public Employee(final Subject subject) {
    this.subject = subject;
  }

  public long getEmployeeId() {
    return employeeId;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(final Subject subject) {
    this.subject = subject;
  }

  public Manager getManager() {
    return manager;
  }

  public void setManager(final Manager manager) {
    this.manager = manager;
  }
}
