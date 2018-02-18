package org.openhr.application.employee.repository;

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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class EmployeeRepositoryTest {
  private final static Employee employee1 = new Employee();
  private final static Employee employee2 = new Employee();
  private final static Manager manager = new Manager();

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Test
  public void getEmployeesOfManagerShouldReturnEmployeesAssignedToManager() {
    final Session session = sessionFactory.getCurrentSession();
    session.save(manager);
    employee1.setManager(manager);
    employee2.setManager(manager);
    session.save(employee1);
    session.save(employee2);
    final List<Employee> actualEmployees = employeeRepository.getEmployeesOfManager(manager.getSubjectId());

    assertEquals(2, actualEmployees.size());
    assertSame(employee1, actualEmployees.get(0));
    assertSame(employee2, actualEmployees.get(1));
  }
}
