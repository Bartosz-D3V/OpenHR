package org.openhr.application.manager.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

  @Test
  public void getManagerShouldReturnManagerById() throws SubjectDoesNotExistException {
    final Session session = sessionFactory.openSession();
    session.save(mockManager);
    session.close();
    final Manager manager = managerDAO.getManager(mockManager.getSubjectId());

    assertEquals(mockManager.getSubjectId(), manager.getSubjectId());
  }

  @Test
  public void getEmployeesShouldReturnSetOfEmployeesForParticularManager() throws SubjectDoesNotExistException {
    final Session session = sessionFactory.openSession();
    mockEmployee1.setManager(mockManager);
    mockEmployee2.setManager(mockManager);
    final Set<Employee> employees = new HashSet<>();
    employees.add(mockEmployee1);
    employees.add(mockEmployee2);
    mockManager.setEmployees(employees);
    final long managerId = (long) session.save(mockManager);
    session.close();
    final Set<Employee> employees2 = managerDAO.getEmployees(managerId);

    assertEquals(employees2.size(), employees.size());
  }

  @Test
  public void addManagerShouldAddManagerToDB() {
    managerDAO.addManager(mockManager);
    final Session session = sessionFactory.openSession();
    final Manager actualManager = session.get(Manager.class, mockManager.getSubjectId());
    session.close();

    assertNotEquals(actualManager.getSubjectId(), 0);
    assertEquals(mockManager.getSubjectId(), actualManager.getSubjectId());
  }
}
