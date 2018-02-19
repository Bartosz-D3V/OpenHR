package org.openhr.application.employee.dao;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class EmployeeDAOTest {
  private final static Employee mockEmployee = new Employee("John", "Xavier", new PersonalInformation(),
    new ContactInformation(), new EmployeeInformation(), new HrInformation(), new User("1", ""));
  private final static Manager mockManager = new Manager("John", "Xavier", new PersonalInformation(),
    new ContactInformation(), new EmployeeInformation(), new HrInformation(), new User("2", ""));

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private EmployeeDAO employeeDAO;

  @Test
  public void setEmployeeManagerShouldSetManagerAndReturnIt() {
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockManager);
    session.save(mockEmployee);
    session.flush();

    final Manager actualManager = employeeDAO.setEmployeeManager(mockManager.getSubjectId(), mockEmployee);
    final Employee actualEmployee = session.get(Employee.class, mockEmployee.getSubjectId());

    assertSame(mockManager.getSubjectId(), actualManager.getSubjectId());
    assertSame(mockManager.getSubjectId(), actualEmployee.getManager().getSubjectId());
  }
}
