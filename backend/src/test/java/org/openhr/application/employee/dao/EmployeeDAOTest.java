package org.openhr.application.employee.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class EmployeeDAOTest {
  private final static Manager manager = new Manager();
  private final static Manager manager2 = new Manager();
  private final static Employee employee = new Employee();

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private EmployeeDAO employeeDAO;

  @Test
  public void setEmployeeManagerShouldSetManagerAndReturnIt() {
    final Session session = sessionFactory.getCurrentSession();
    employee.setManager(manager);
    session.save(manager);
    session.save(employee);
    assertSame(manager, employee.getManager());

    employee.setManager(manager2);
    final Manager actualManager = employeeDAO.setEmployeeManager(employee.getSubjectId(), employee);
    final Employee actualEmployee = session.get(Employee.class, employee.getSubjectId());

    assertSame(manager2, actualManager);
    assertSame(manager2, actualEmployee.getManager());
  }
}
