package org.openhr.application.manager.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.Manager;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ManagerDAOTest {
  private final static Subject mockSubject1 = new Employee("John", "Xavier", new PersonalInformation(),
    new ContactInformation(), new EmployeeInformation(), new HrInformation(), new User());
  private final static Subject mockSubject2 = new Employee("Alex", "White", new PersonalInformation(),
    new ContactInformation(), new EmployeeInformation(), new HrInformation(), new User());
  private final static Employee mockEmployee1 = new Employee();
  private final static Employee mockEmployee2 = new Employee();
  private final static Manager mockManager = new Manager("John", "Xavier", new PersonalInformation(),
    new ContactInformation(), new EmployeeInformation(), new HrInformation(), new User("", ""));

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private ManagerDAO managerDAO;

  @Before
  public void setUp() {
    final Set<Employee> employees = new HashSet<>();
    employees.add(mockEmployee1);
    employees.add(mockEmployee2);
    mockManager.setEmployees(employees);
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockManager);
  }

  @Test
  public void getManagerShouldReturnManagerById() throws SubjectDoesNotExistException {
    final Manager manager = managerDAO.getManager(mockManager.getSubjectId());

    assertEquals(mockManager.getSubjectId(), manager.getSubjectId());
  }

  @Test
  public void getEmployeesShouldReturnSetOfEmployeesForParticularManager() throws SubjectDoesNotExistException {
    final Set<Employee> employees2 = managerDAO.getEmployees(mockManager.getSubjectId());

    assertEquals(2, employees2.size());
  }

  @Test
  public void addManagerShouldAddManagerToDB() {
    final Session session = sessionFactory.getCurrentSession();
    managerDAO.addManager(mockManager);
    final Manager actualManager = session.get(Manager.class, mockManager.getSubjectId());

    assertNotEquals(actualManager.getSubjectId(), 0);
    assertEquals(mockManager.getSubjectId(), actualManager.getSubjectId());
  }
}
