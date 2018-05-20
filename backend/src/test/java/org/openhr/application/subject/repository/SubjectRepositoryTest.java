package org.openhr.application.subject.repository;

import static org.junit.Assert.assertEquals;

import java.util.UUID;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class SubjectRepositoryTest {
  private final Address mockAddress =
      new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL", "London", "UK");
  private final PersonalInformation mockPersonalInformation =
      new PersonalInformation("John", "Xavier", "Alex", null);
  private final ContactInformation mockContactInformation =
      new ContactInformation("0123456789", "j.x@g.com", mockAddress);
  private final EmployeeInformation mockEmployeeInformation =
      new EmployeeInformation("S8821 B", "Tester", "Core", "12A", null, null);
  private final HrInformation mockHrInformation = new HrInformation(25L);
  private final User mockUser = new User(UUID.randomUUID().toString().substring(0, 19), "testPass");
  private final Employee mockSubject =
      new Employee(
          mockPersonalInformation,
          mockContactInformation,
          mockEmployeeInformation,
          mockHrInformation,
          mockUser);

  @Autowired private SessionFactory sessionFactory;

  @Autowired private SubjectRepository subjectRepository;

  @Test
  public void getAllowanceShouldReturnAllowance() throws HibernateException {
    final Session session = sessionFactory.getCurrentSession();
    session.save(mockSubject);
    session.flush();

    final long actualAllowance = subjectRepository.getAllowance(mockSubject.getSubjectId());

    assertEquals(25L, actualAllowance);
  }

  @Test
  public void getUsedAllowanceShouldReturnUsedAllowance() throws HibernateException {
    final Session session = sessionFactory.getCurrentSession();
    final HrInformation mockHrInformation2 = new HrInformation(20L);
    mockHrInformation2.setUsedAllowance(10L);
    mockSubject.setHrInformation(mockHrInformation2);
    session.save(mockSubject);
    session.flush();

    final long actualUsedAllowance = subjectRepository.getUsedAllowance(mockSubject.getSubjectId());

    assertEquals(10L, actualUsedAllowance);
  }
}
