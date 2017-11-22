package org.openhr.dao.subject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.domain.address.Address;
import org.openhr.domain.subject.ContactInformation;
import org.openhr.domain.subject.EmployeeInformation;
import org.openhr.domain.subject.PersonalInformation;
import org.openhr.domain.subject.Subject;
import org.openhr.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SubjectDAOTest {

  private final static Address mockAddress = new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL",
    "London", "UK");
  private final static PersonalInformation mockPersonalInformation = new PersonalInformation("John", null);
  private final static ContactInformation mockContactInformation = new ContactInformation("0123456789", "j.x@g.com", mockAddress);
  private final static EmployeeInformation mockEmployeeInformation = new EmployeeInformation("S8821 B", "Tester", "12A", null, null);
  private final static Subject mockSubject = new Subject("John", "Xavier", Role.EMPLOYEE, mockPersonalInformation,
    mockContactInformation, mockEmployeeInformation);

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private SubjectDAO subjectDAO;

  @Test(expected = SubjectDoesNotExistException.class)
  public void getSubjectDetailsShouldThrowExceptionIfSubjectDoesNotExist() throws SubjectDoesNotExistException {
    subjectDAO.getSubjectDetails(123L);
  }

  @Test
  public void getSubjectDetailsShouldReturnSubjectObjectBySubjectId() throws SubjectDoesNotExistException {
    final Session session = sessionFactory.openSession();
    session.saveOrUpdate(mockSubject);
    session.close();
    final Subject actualSubject = subjectDAO.getSubjectDetails(mockSubject.getSubjectId());

    assertEquals(mockSubject.getSubjectId(), actualSubject.getSubjectId());
    assertEquals(mockSubject.getFirstName(), actualSubject.getFirstName());
    assertEquals(mockSubject.getLastName(), actualSubject.getLastName());
    assertEquals(mockSubject.getRole(), actualSubject.getRole());
    assertEquals(mockSubject.getPersonalInformation().getMiddleName(),
      actualSubject.getPersonalInformation().getMiddleName());
    assertEquals(mockSubject.getPersonalInformation().getDateOfBirth(),
      actualSubject.getPersonalInformation().getDateOfBirth());
    assertEquals(mockSubject.getContactInformation().getTelephone(),
      actualSubject.getContactInformation().getTelephone());
    assertEquals(mockSubject.getContactInformation().getEmail(),
      actualSubject.getContactInformation().getEmail());
    assertEquals(mockSubject.getContactInformation().getAddress().getFirstLineAddress(),
      actualSubject.getContactInformation().getAddress().getFirstLineAddress());
    assertEquals(mockSubject.getContactInformation().getAddress().getSecondLineAddress(),
      actualSubject.getContactInformation().getAddress().getSecondLineAddress());
    assertEquals(mockSubject.getContactInformation().getAddress().getThirdLineAddress(),
      actualSubject.getContactInformation().getAddress().getThirdLineAddress());
    assertEquals(mockSubject.getContactInformation().getAddress().getCity(),
      actualSubject.getContactInformation().getAddress().getCity());
    assertEquals(mockSubject.getContactInformation().getAddress().getPostcode(),
      actualSubject.getContactInformation().getAddress().getPostcode());
    assertEquals(mockSubject.getContactInformation().getAddress().getCountry(),
      actualSubject.getContactInformation().getAddress().getCountry());
    assertEquals(mockSubject.getEmployeeInformation().getEmployeeId(),
      actualSubject.getEmployeeInformation().getEmployeeId());
    assertEquals(mockSubject.getEmployeeInformation().getNationalInsuranceNumber(),
      actualSubject.getEmployeeInformation().getNationalInsuranceNumber());
    assertEquals(mockSubject.getEmployeeInformation().getPosition(),
      actualSubject.getEmployeeInformation().getPosition());
    assertEquals(mockSubject.getEmployeeInformation().getStartDate(),
      actualSubject.getEmployeeInformation().getStartDate());
    assertEquals(mockSubject.getEmployeeInformation().getEndDate(),
      actualSubject.getEmployeeInformation().getEndDate());
  }

  @Test
  public void addSubjectShouldInsertSubjectToDatabase() {
    subjectDAO.addSubject(mockSubject);
    final Session session = sessionFactory.openSession();
    final Subject actualSubject = session.get(Subject.class, mockSubject.getSubjectId());
    session.close();

    assertEquals(mockSubject.getSubjectId(), actualSubject.getSubjectId());
    assertEquals(mockSubject.getFirstName(), actualSubject.getFirstName());
    assertEquals(mockSubject.getLastName(), actualSubject.getLastName());
    assertEquals(mockSubject.getRole(), actualSubject.getRole());
    assertEquals(mockSubject.getPersonalInformation().getMiddleName(),
      actualSubject.getPersonalInformation().getMiddleName());
    assertEquals(mockSubject.getPersonalInformation().getDateOfBirth(),
      actualSubject.getPersonalInformation().getDateOfBirth());
    assertEquals(mockSubject.getContactInformation().getTelephone(),
      actualSubject.getContactInformation().getTelephone());
    assertEquals(mockSubject.getContactInformation().getEmail(),
      actualSubject.getContactInformation().getEmail());
    assertEquals(mockSubject.getContactInformation().getAddress().getFirstLineAddress(),
      actualSubject.getContactInformation().getAddress().getFirstLineAddress());
    assertEquals(mockSubject.getContactInformation().getAddress().getSecondLineAddress(),
      actualSubject.getContactInformation().getAddress().getSecondLineAddress());
    assertEquals(mockSubject.getContactInformation().getAddress().getThirdLineAddress(),
      actualSubject.getContactInformation().getAddress().getThirdLineAddress());
    assertEquals(mockSubject.getContactInformation().getAddress().getCity(),
      actualSubject.getContactInformation().getAddress().getCity());
    assertEquals(mockSubject.getContactInformation().getAddress().getPostcode(),
      actualSubject.getContactInformation().getAddress().getPostcode());
    assertEquals(mockSubject.getContactInformation().getAddress().getCountry(),
      actualSubject.getContactInformation().getAddress().getCountry());
    assertEquals(mockSubject.getEmployeeInformation().getEmployeeId(),
      actualSubject.getEmployeeInformation().getEmployeeId());
    assertEquals(mockSubject.getEmployeeInformation().getNationalInsuranceNumber(),
      actualSubject.getEmployeeInformation().getNationalInsuranceNumber());
    assertEquals(mockSubject.getEmployeeInformation().getPosition(),
      actualSubject.getEmployeeInformation().getPosition());
    assertEquals(mockSubject.getEmployeeInformation().getStartDate(),
      actualSubject.getEmployeeInformation().getStartDate());
    assertEquals(mockSubject.getEmployeeInformation().getEndDate(),
      actualSubject.getEmployeeInformation().getEndDate());
  }

  @Test
  public void updateSubjectPersonalInformationShouldAlterExistingPersonalInformationInDatabase()
    throws SubjectDoesNotExistException {
    Session session = sessionFactory.openSession();
    session.saveOrUpdate(mockSubject);
    session.close();

    mockSubject.getPersonalInformation().setMiddleName("Michel");
    mockSubject.getPersonalInformation().setDateOfBirth(null);
    subjectDAO.updateSubjectPersonalInformation(mockSubject.getSubjectId(), mockSubject.getPersonalInformation());

    session = sessionFactory.openSession();
    final Subject subject = session.get(Subject.class, mockSubject.getSubjectId());
    session.close();

    assertEquals(mockSubject.getPersonalInformation().getMiddleName(),
      subject.getPersonalInformation().getMiddleName());
    assertEquals(mockSubject.getPersonalInformation().getDateOfBirth(),
      subject.getPersonalInformation().getDateOfBirth());
  }

  @Test(expected = SubjectDoesNotExistException.class)
  public void updateSubjectPersonalInformationShouldThrowExceptionIfSubjectDoesNotExist()
    throws SubjectDoesNotExistException {
    subjectDAO.updateSubjectPersonalInformation(123L, mockPersonalInformation);
  }

  @Test
  public void updateSubjectPersonalInformationShouldUpdatePersonalInformationBySubjectIdAndPersonalInformation()
    throws SubjectDoesNotExistException {
    mockPersonalInformation.setDateOfBirth(null);
    mockPersonalInformation.setMiddleName("Bill");

    Session session = sessionFactory.openSession();
    session.saveOrUpdate(mockSubject);
    subjectDAO.updateSubjectPersonalInformation(mockSubject.getSubjectId(), mockPersonalInformation);
    final Subject subject = session.get(Subject.class, mockSubject.getSubjectId());
    session.close();

    assertEquals(mockPersonalInformation.getDateOfBirth(), subject.getPersonalInformation().getDateOfBirth());
    assertEquals(mockPersonalInformation.getMiddleName(), subject.getPersonalInformation().getMiddleName());
  }

  @Test(expected = SubjectDoesNotExistException.class)
  public void updateSubjectContactInformationShouldThrowExceptionIfSubjectDoesNotExist()
    throws SubjectDoesNotExistException {
    subjectDAO.updateSubjectContactInformation(123L, mockContactInformation);
  }

  @Test
  public void updateSubjectContactInformationShouldUpdateContactInformationBySubjectIdAndContactInformation()
    throws SubjectDoesNotExistException {
    mockContactInformation.setEmail("j.x@mail.com");
    mockContactInformation.setTelephone("987654321");
    mockContactInformation.getAddress().setFirstLineAddress("Sample street");
    mockContactInformation.getAddress().setSecondLineAddress("Sample street 2nd line");
    mockContactInformation.getAddress().setThirdLineAddress("Sample street 3rd line");
    mockContactInformation.getAddress().setPostcode("89-123");
    mockContactInformation.getAddress().setCity("Warsaw");
    mockContactInformation.getAddress().setCountry("Poland");

    final Session session = sessionFactory.openSession();
    session.saveOrUpdate(mockSubject);
    subjectDAO.updateSubjectContactInformation(mockSubject.getSubjectId(), mockContactInformation);
    final Subject subject = session.get(Subject.class, mockSubject.getSubjectId());
    session.close();

    assertEquals(mockSubject.getContactInformation().getEmail(), subject.getContactInformation().getEmail());
    assertEquals(mockSubject.getContactInformation().getTelephone(), subject.getContactInformation().getTelephone());
    assertEquals(mockSubject.getContactInformation().getAddress().getFirstLineAddress(),
      subject.getContactInformation().getAddress().getFirstLineAddress());
    assertEquals(mockSubject.getContactInformation().getAddress().getSecondLineAddress(),
      subject.getContactInformation().getAddress().getSecondLineAddress());
    assertEquals(mockSubject.getContactInformation().getAddress().getThirdLineAddress(),
      subject.getContactInformation().getAddress().getThirdLineAddress());
    assertEquals(mockSubject.getContactInformation().getAddress().getCity(),
      subject.getContactInformation().getAddress().getCity());
    assertEquals(mockSubject.getContactInformation().getAddress().getPostcode(),
      subject.getContactInformation().getAddress().getPostcode());
    assertEquals(mockSubject.getContactInformation().getAddress().getCountry(),
      subject.getContactInformation().getAddress().getCountry());
  }

  @Test(expected = SubjectDoesNotExistException.class)
  public void updateSubjectEmployeeInformationShouldThrowExceptionIfSubjectDoesNotExist()
    throws SubjectDoesNotExistException {
    subjectDAO.updateSubjectEmployeeInformation(123L, mockEmployeeInformation);
  }

  @Test
  public void updateSubjectEmployeeInformationShouldUpdateEmployeeInformationBySubjectIdAndEmployeeInformation()
    throws SubjectDoesNotExistException {
    Session session = sessionFactory.openSession();
    session.saveOrUpdate(mockSubject);
    session.close();

    mockSubject.getEmployeeInformation().setNationalInsuranceNumber("HJGS723 B 12");
    mockSubject.getEmployeeInformation().setPosition("Developer");
    mockSubject.getEmployeeInformation().setStartDate(null);
    mockSubject.getEmployeeInformation().setEndDate(null);
    subjectDAO.updateSubjectEmployeeInformation(mockSubject.getSubjectId(), mockSubject.getEmployeeInformation());

    session = sessionFactory.openSession();
    final Subject subject = session.get(Subject.class, mockSubject.getSubjectId());
    session.close();

    assertEquals(mockSubject.getEmployeeInformation().getEmployeeId(),
      subject.getEmployeeInformation().getEmployeeId());
    assertEquals(mockSubject.getEmployeeInformation().getNationalInsuranceNumber(),
      subject.getEmployeeInformation().getNationalInsuranceNumber());
    assertEquals(mockSubject.getEmployeeInformation().getPosition(), subject.getEmployeeInformation().getPosition());
    assertEquals(mockSubject.getEmployeeInformation().getStartDate(), subject.getEmployeeInformation().getStartDate());
    assertEquals(mockSubject.getEmployeeInformation().getEndDate(), subject.getEmployeeInformation().getEndDate());
  }

  @Test(expected = SubjectDoesNotExistException.class)
  public void deleteSubjectShouldShouldThrowExceptionIfSubjectDoesNotExist() throws SubjectDoesNotExistException {
    subjectDAO.deleteSubject(678L);
  }

  @Test
  public void deleteSubjectShouldDeleteSubject() throws SubjectDoesNotExistException {
    Session session = sessionFactory.openSession();
    session.saveOrUpdate(mockSubject);
    session.close();

    subjectDAO.deleteSubject(mockSubject.getSubjectId());
    session = sessionFactory.openSession();
    final Subject subject = session.get(Subject.class, mockSubject.getSubjectId());
    session.close();

    assertNull(subject);
  }

}
