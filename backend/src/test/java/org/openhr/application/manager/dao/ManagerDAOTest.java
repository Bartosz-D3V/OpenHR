package org.openhr.application.manager.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ManagerDAOTest {
  private static final Employee mockEmployee1 =
      new Employee(
          new PersonalInformation(),
          new ContactInformation(),
          new EmployeeInformation(),
          new HrInformation(),
          new User("1", ""));
  private static final Employee mockEmployee2 =
      new Employee(
          new PersonalInformation("John", "Black", "Alex", LocalDate.now()),
          new ContactInformation(),
          new EmployeeInformation(),
          new HrInformation(),
          new User("2", ""));
  private static final Manager mockManager =
      new Manager(
          new PersonalInformation("John", "Black", "Alex", LocalDate.now()),
          new ContactInformation(),
          new EmployeeInformation(),
          new HrInformation(),
          new User("3", ""));

  @Autowired private SessionFactory sessionFactory;

  @Autowired private ManagerDAO managerDAO;

  @Test
  public void getEmployeesShouldReturnSetOfEmployeesForParticularManager()
      throws SubjectDoesNotExistException {
    final Set<Employee> employees = new HashSet<>();
    employees.add(mockEmployee1);
    employees.add(mockEmployee2);
    mockManager.setEmployees(employees);
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockManager);
    final Set<Employee> employees2 = managerDAO.getManagersEmployees(mockManager.getSubjectId());

    assertEquals(2, employees2.size());
  }

  @Test
  public void addManagerShouldAddManagerToDB() {
    final Manager mockManager2 =
        new Manager(
            new PersonalInformation("John", "Black", "Alex", LocalDate.now()),
            new ContactInformation(),
            new EmployeeInformation(),
            new HrInformation(),
            new User("4", ""));
    managerDAO.addManager(mockManager2);
    final Session session = sessionFactory.getCurrentSession();
    final Manager actualManager = session.get(Manager.class, mockManager2.getSubjectId());

    assertNotEquals(actualManager.getSubjectId(), 0);
    assertEquals(mockManager2.getSubjectId(), actualManager.getSubjectId());
  }
}
