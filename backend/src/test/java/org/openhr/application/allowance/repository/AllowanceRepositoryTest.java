package org.openhr.application.allowance.repository;

import static org.junit.Assert.assertEquals;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.address.Address;
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
public class AllowanceRepositoryTest {
  private static final Address mockAddress =
      new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL", "London", "UK");
  private static final PersonalInformation mockPersonalInformation =
      new PersonalInformation("John", "Xavier", "Alex", null);
  private static final ContactInformation mockContactInformation =
      new ContactInformation("0123456789", "j.x@g.com", mockAddress);
  private static final EmployeeInformation mockEmployeeInformation =
      new EmployeeInformation("S8821 B", "Tester", "Core", "12A", null, null);
  private static final HrInformation mockHrInformation = new HrInformation(25);
  private static final User mockUser = new User("Jhn13", "testPass");
  private static final Employee mockSubject =
      new Employee(
          mockPersonalInformation,
          mockContactInformation,
          mockEmployeeInformation,
          mockHrInformation,
          mockUser);

  @Autowired private SessionFactory sessionFactory;

  @Autowired private AllowanceRepository allowanceRepository;

  @Before
  public void setUp() {
    final Session session = sessionFactory.getCurrentSession();
    mockHrInformation.setAllowance(25);
    mockHrInformation.setHrInformationId(2L);
    session.save(mockPersonalInformation);
    session.save(mockContactInformation);
    session.save(mockEmployeeInformation);
    session.save(mockHrInformation);
    session.saveOrUpdate(mockUser);
    session.saveOrUpdate(mockSubject);
    session.flush();
  }

  @Test
  public void getAllowanceShouldReturnAllowance() throws HibernateException {
    final long actualAllowance = allowanceRepository.getAllowance(mockSubject.getSubjectId());

    assertEquals(mockHrInformation.getAllowance(), actualAllowance);
  }

  @Test
  public void getUsedAllowanceShouldReturnUsedAllowance() throws HibernateException {
    final Session session = sessionFactory.getCurrentSession();
    final HrInformation mockHrInformation2 = new HrInformation(20);
    mockHrInformation2.setUsedAllowance(10);
    mockSubject.setHrInformation(mockHrInformation2);
    session.merge(mockSubject);
    final long actualUsedAllowance =
        allowanceRepository.getUsedAllowance(mockSubject.getSubjectId());

    assertEquals(mockHrInformation.getUsedAllowance(), actualUsedAllowance);
  }
}
