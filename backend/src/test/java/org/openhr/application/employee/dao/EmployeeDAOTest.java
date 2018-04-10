package org.openhr.application.employee.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.time.LocalDate;
import java.util.UUID;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class EmployeeDAOTest {
  @Autowired private SessionFactory sessionFactory;

  @Autowired private EmployeeDAO employeeDAO;

  @Test
  public void getEmployeeShouldReturnSingleEmployee() {
    final Session session = sessionFactory.getCurrentSession();
    final Employee mockEmployee = getMockEmployee();
    session.save(mockEmployee);

    final Employee actualEmployee = employeeDAO.getEmployee(mockEmployee.getSubjectId());

    assertNotEquals(0, actualEmployee.getSubjectId());
    assertSame(mockEmployee, actualEmployee);
  }

  @Test
  public void createEmployeeShouldCreateEmployeeAndReturnIt() {
    final Employee mockEmployee = getMockEmployee();
    final Employee actualEmployee = employeeDAO.createEmployee(mockEmployee);
    final Session session = sessionFactory.getCurrentSession();
    final Employee employee = session.get(Employee.class, actualEmployee.getSubjectId());

    assertNotEquals(0, actualEmployee.getSubjectId());
    assertNotNull(employee);
    assertSame(mockEmployee, actualEmployee);
    assertSame(mockEmployee.getSubjectId(), employee.getSubjectId());
  }

  @Test
  public void updateEmployeeShouldUpdateEmployeeAndReturnIt() {
    final Employee mockEmployee = getMockEmployee();
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockEmployee);
    session.flush();

    mockEmployee.getPersonalInformation().setLastName("UpdatedLastName");
    final Employee employee = employeeDAO.updateEmployee(mockEmployee.getSubjectId(), mockEmployee);
    final Employee savedEmployee = session.get(Employee.class, mockEmployee.getSubjectId());

    assertNotNull(employee);
    assertNotNull(savedEmployee);
    assertEquals(mockEmployee.getPersonalInformation(), employee.getPersonalInformation());
    assertEquals(mockEmployee.getPersonalInformation(), savedEmployee.getPersonalInformation());
  }

  @Test
  public void setEmployeeManagerShouldSetManagerAndReturnIt() {
    final Employee mockEmployee = getMockEmployee();
    final Manager mockManager = getMockManager();
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockManager);
    session.save(mockEmployee);
    session.flush();

    final Manager actualManager =
        employeeDAO.setManagerToEmployee(mockEmployee.getSubjectId(), mockManager);

    assertSame(mockManager.getSubjectId(), actualManager.getSubjectId());
  }

  private static Manager getMockManager() {
    final String randomUsername = UUID.randomUUID().toString().subSequence(0, 19).toString();
    return new Manager(
        new PersonalInformation("John", "Black", "Alex", LocalDate.now()),
        new ContactInformation(),
        new EmployeeInformation(),
        new HrInformation(),
        new User(randomUsername, ""));
  }

  private static Employee getMockEmployee() {
    final String randomUsername = UUID.randomUUID().toString().subSequence(0, 19).toString();
    return new Employee(
        new PersonalInformation("John", "Black", "Alex", LocalDate.now()),
        new ContactInformation(),
        new EmployeeInformation(),
        new HrInformation(),
        new User(randomUsername, ""));
  }
}
