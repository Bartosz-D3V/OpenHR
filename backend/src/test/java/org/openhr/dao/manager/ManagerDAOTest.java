package org.openhr.dao.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.domain.subject.ContactInformation;
import org.openhr.domain.subject.Employee;
import org.openhr.domain.subject.EmployeeInformation;
import org.openhr.domain.subject.Manager;
import org.openhr.domain.subject.PersonalInformation;
import org.openhr.domain.subject.Subject;
import org.openhr.exception.SubjectDoesNotExistException;
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
  private final static Subject mockSubject1 = new Subject("John", "Xavier", new PersonalInformation(),
    new ContactInformation(), new EmployeeInformation());
  private final static Subject mockSubject2 = new Subject("Alex", "White", new PersonalInformation(),
    new ContactInformation(), new EmployeeInformation());
  private final static Employee mockEmployee1 = new Employee(mockSubject1);
  private final static Employee mockEmployee2 = new Employee(mockSubject2);

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private ManagerDAO managerDAO;

  @Test
  public void getManagerShouldReturnManagerById() throws SubjectDoesNotExistException {
    final Manager mockManager = new Manager();
    final Session session = sessionFactory.openSession();
    long id = (long) session.save(mockManager);
    session.close();
    final Manager manager = managerDAO.getManager(id);

    assertEquals(id, manager.getManagerId());
  }

  @Test
  public void getEmployeesShouldReturnSetOfEmployeesForParticularManager() throws SubjectDoesNotExistException {
    final Manager mockManager = new Manager();
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
    Manager mockManager = new Manager();
    mockManager = managerDAO.addManager(mockManager);
    final Session session = sessionFactory.openSession();
    final Manager actualManager = session.get(Manager.class, mockManager.getManagerId());
    session.close();

    assertNotEquals(actualManager.getManagerId(), 0);
    assertEquals(mockManager.getManagerId(), actualManager.getManagerId());
  }

  @Test
  public void addEmployeeToManagerShouldAddEmployeeToManagersEmployeesSet() throws SubjectDoesNotExistException {
    Manager mockManager = new Manager();
    Session session = sessionFactory.openSession();
    mockManager = (Manager) session.merge(mockManager);
    session.save(mockSubject1);
    session.save(mockSubject2);
    mockEmployee1.setManager(mockManager);
    mockEmployee2.setManager(mockManager);
    session.save(mockEmployee1);
    session.save(mockEmployee2);
    session.close();
    managerDAO.addEmployeeToManager(mockEmployee1, mockManager.getManagerId());
    managerDAO.addEmployeeToManager(mockEmployee2, mockManager.getManagerId());
    session = sessionFactory.openSession();
    mockManager = session.get(Manager.class, mockManager.getManagerId());
    session.close();

    assertNotEquals(0, mockManager.getManagerId());
    assertEquals(2, mockManager.getEmployees().size());
  }
}
