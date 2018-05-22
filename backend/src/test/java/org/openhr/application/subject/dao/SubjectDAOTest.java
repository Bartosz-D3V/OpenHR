package org.openhr.application.subject.dao;

import static org.junit.Assert.assertEquals;

import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.address.Address;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class SubjectDAOTest {
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

  @Autowired private SubjectDAO subjectDAO;

  @Before
  public void setUp() {
    final Session session = sessionFactory.getCurrentSession();
    mockHrInformation.setAllowance(25L);
    mockHrInformation.setHrInformationId(2L);
    session.save(mockPersonalInformation);
    session.save(mockContactInformation);
    session.save(mockEmployeeInformation);
    session.save(mockHrInformation);
    session.saveOrUpdate(mockUser);
    session.saveOrUpdate(mockSubject);
    session.flush();
  }

  @Test(expected = SubjectDoesNotExistException.class)
  public void getSubjectDetailsShouldThrowExceptionIfSubjectDoesNotExist()
      throws SubjectDoesNotExistException {
    subjectDAO.getSubjectDetails(123L);
  }

  @Test
  public void getSubjectDetailsShouldReturnSubjectObjectBySubjectId()
      throws SubjectDoesNotExistException {
    final Subject actualSubject = subjectDAO.getSubjectDetails(mockSubject.getSubjectId());

    assertEquals(mockSubject.getSubjectId(), actualSubject.getSubjectId());
    assertEquals(
        mockSubject.getPersonalInformation().getFirstName(),
        actualSubject.getPersonalInformation().getFirstName());
    assertEquals(
        mockSubject.getPersonalInformation().getLastName(),
        actualSubject.getPersonalInformation().getLastName());
    assertEquals(
        mockSubject.getPersonalInformation().getMiddleName(),
        actualSubject.getPersonalInformation().getMiddleName());
    assertEquals(
        mockSubject.getPersonalInformation().getDateOfBirth(),
        actualSubject.getPersonalInformation().getDateOfBirth());
    assertEquals(
        mockSubject.getContactInformation().getTelephone(),
        actualSubject.getContactInformation().getTelephone());
    assertEquals(
        mockSubject.getContactInformation().getEmail(),
        actualSubject.getContactInformation().getEmail());
    assertEquals(
        mockSubject.getContactInformation().getAddress().getFirstLineAddress(),
        actualSubject.getContactInformation().getAddress().getFirstLineAddress());
    assertEquals(
        mockSubject.getContactInformation().getAddress().getSecondLineAddress(),
        actualSubject.getContactInformation().getAddress().getSecondLineAddress());
    assertEquals(
        mockSubject.getContactInformation().getAddress().getThirdLineAddress(),
        actualSubject.getContactInformation().getAddress().getThirdLineAddress());
    assertEquals(
        mockSubject.getContactInformation().getAddress().getCity(),
        actualSubject.getContactInformation().getAddress().getCity());
    assertEquals(
        mockSubject.getContactInformation().getAddress().getPostcode(),
        actualSubject.getContactInformation().getAddress().getPostcode());
    assertEquals(
        mockSubject.getContactInformation().getAddress().getCountry(),
        actualSubject.getContactInformation().getAddress().getCountry());
    assertEquals(
        mockSubject.getEmployeeInformation().getEmployeeNumber(),
        actualSubject.getEmployeeInformation().getEmployeeNumber());
    assertEquals(
        mockSubject.getEmployeeInformation().getNationalInsuranceNumber(),
        actualSubject.getEmployeeInformation().getNationalInsuranceNumber());
    assertEquals(
        mockSubject.getEmployeeInformation().getPosition(),
        actualSubject.getEmployeeInformation().getPosition());
    assertEquals(
        mockSubject.getEmployeeInformation().getStartDate(),
        actualSubject.getEmployeeInformation().getStartDate());
    assertEquals(
        mockSubject.getEmployeeInformation().getEndDate(),
        actualSubject.getEmployeeInformation().getEndDate());
  }

  @Test(expected = SubjectDoesNotExistException.class)
  @Ignore
  public void updateSubjectPersonalInformationShouldThrowExceptionIfSubjectDoesNotExist()
      throws SubjectDoesNotExistException {
    subjectDAO.updateSubjectPersonalInformation(123L, mockPersonalInformation);
  }

  @Test
  public void
      updateSubjectPersonalInformationShouldUpdatePersonalInformationBySubjectIdAndPersonalInformation()
          throws SubjectDoesNotExistException {
    mockPersonalInformation.setDateOfBirth(null);
    mockPersonalInformation.setMiddleName("Bill");

    subjectDAO.updateSubjectPersonalInformation(
        mockSubject.getSubjectId(), mockPersonalInformation);
    final Session session = sessionFactory.getCurrentSession();
    final Subject subject = session.get(Subject.class, mockSubject.getSubjectId());

    assertEquals(
        mockPersonalInformation.getDateOfBirth(),
        subject.getPersonalInformation().getDateOfBirth());
    assertEquals(
        mockPersonalInformation.getMiddleName(), subject.getPersonalInformation().getMiddleName());
  }

  @Test(expected = SubjectDoesNotExistException.class)
  public void updateSubjectContactInformationShouldThrowExceptionIfSubjectDoesNotExist()
      throws SubjectDoesNotExistException {
    subjectDAO.updateSubjectContactInformation(123L, mockContactInformation);
  }

  @Test
  public void
      updateSubjectContactInformationShouldUpdateContactInformationBySubjectIdAndContactInformation()
          throws SubjectDoesNotExistException {
    mockContactInformation.setEmail("j.x@mail.com");
    mockContactInformation.setTelephone("987654321");
    mockContactInformation.getAddress().setFirstLineAddress("Sample street");
    mockContactInformation.getAddress().setSecondLineAddress("Sample street 2nd line");
    mockContactInformation.getAddress().setThirdLineAddress("Sample street 3rd line");
    mockContactInformation.getAddress().setPostcode("89-123");
    mockContactInformation.getAddress().setCity("Warsaw");
    mockContactInformation.getAddress().setCountry("Poland");

    subjectDAO.updateSubjectContactInformation(mockSubject.getSubjectId(), mockContactInformation);
    final Session session = sessionFactory.getCurrentSession();
    final Subject subject = session.get(Subject.class, mockSubject.getSubjectId());

    assertEquals(
        mockSubject.getContactInformation().getEmail(), subject.getContactInformation().getEmail());
    assertEquals(
        mockSubject.getContactInformation().getTelephone(),
        subject.getContactInformation().getTelephone());
    assertEquals(
        mockSubject.getContactInformation().getAddress().getFirstLineAddress(),
        subject.getContactInformation().getAddress().getFirstLineAddress());
    assertEquals(
        mockSubject.getContactInformation().getAddress().getSecondLineAddress(),
        subject.getContactInformation().getAddress().getSecondLineAddress());
    assertEquals(
        mockSubject.getContactInformation().getAddress().getThirdLineAddress(),
        subject.getContactInformation().getAddress().getThirdLineAddress());
    assertEquals(
        mockSubject.getContactInformation().getAddress().getCity(),
        subject.getContactInformation().getAddress().getCity());
    assertEquals(
        mockSubject.getContactInformation().getAddress().getPostcode(),
        subject.getContactInformation().getAddress().getPostcode());
    assertEquals(
        mockSubject.getContactInformation().getAddress().getCountry(),
        subject.getContactInformation().getAddress().getCountry());
  }

  @Test(expected = SubjectDoesNotExistException.class)
  public void updateSubjectEmployeeInformationShouldThrowExceptionIfSubjectDoesNotExist()
      throws SubjectDoesNotExistException {
    subjectDAO.updateSubjectEmployeeInformation(123L, mockEmployeeInformation);
  }

  @Test
  public void
      updateSubjectEmployeeInformationShouldUpdateEmployeeInformationBySubjectIdAndEmployeeInformation()
          throws SubjectDoesNotExistException {
    mockSubject.getEmployeeInformation().setNationalInsuranceNumber("HJGS723 B 12");
    mockSubject.getEmployeeInformation().setPosition("Developer");
    mockSubject.getEmployeeInformation().setStartDate(null);
    mockSubject.getEmployeeInformation().setEndDate(null);
    subjectDAO.updateSubjectEmployeeInformation(
        mockSubject.getSubjectId(), mockSubject.getEmployeeInformation());

    final Session session = sessionFactory.getCurrentSession();
    final Subject subject = session.get(Subject.class, mockSubject.getSubjectId());

    assertEquals(
        mockSubject.getEmployeeInformation().getEmployeeNumber(),
        subject.getEmployeeInformation().getEmployeeNumber());
    assertEquals(
        mockSubject.getEmployeeInformation().getNationalInsuranceNumber(),
        subject.getEmployeeInformation().getNationalInsuranceNumber());
    assertEquals(
        mockSubject.getEmployeeInformation().getPosition(),
        subject.getEmployeeInformation().getPosition());
    assertEquals(
        mockSubject.getEmployeeInformation().getStartDate(),
        subject.getEmployeeInformation().getStartDate());
    assertEquals(
        mockSubject.getEmployeeInformation().getEndDate(),
        subject.getEmployeeInformation().getEndDate());
  }
}
