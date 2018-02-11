package org.openhr.subject.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.subject.dao.SubjectDAO;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class SubjectDAOTest {
  private final static Address mockAddress = new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL", "London",
    "UK");
  private final static PersonalInformation mockPersonalInformation = new PersonalInformation("John", null);
  private final static ContactInformation mockContactInformation = new ContactInformation("0123456789", "j.x@g.com",
    mockAddress);
  private final static EmployeeInformation mockEmployeeInformation = new EmployeeInformation("S8821 B", "Tester",
    "Core", "12A", null, null);
  private final static HrInformation mockHrInformation = new HrInformation(25L);
  private final static Subject mockSubject = new Subject("John", "Xavier", mockPersonalInformation,
    mockContactInformation, mockEmployeeInformation, mockHrInformation, new User("Jhn13", "testPass"));

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private SubjectDAO subjectDAO;

  @Rollback
  @Test(expected = SubjectDoesNotExistException.class)
  public void getSubjectDetailsShouldThrowExceptionIfSubjectDoesNotExist() throws SubjectDoesNotExistException {
    subjectDAO.getSubjectDetails(123L);
  }

  @Test
  @Rollback
  public void getSubjectDetailsShouldReturnSubjectObjectBySubjectId() throws SubjectDoesNotExistException {
    final Session session = sessionFactory.openSession();
    session.saveOrUpdate(mockSubject);
    session.close();
    final Subject actualSubject = subjectDAO.getSubjectDetails(mockSubject.getSubjectId());

    assertEquals(mockSubject.getSubjectId(), actualSubject.getSubjectId());
    assertEquals(mockSubject.getFirstName(), actualSubject.getFirstName());
    assertEquals(mockSubject.getLastName(), actualSubject.getLastName());
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
  @Rollback
  public void addSubjectShouldInsertSubjectToDatabase() {
    subjectDAO.createSubject(mockSubject);
    final Session session = sessionFactory.openSession();
    final Subject actualSubject = session.get(Subject.class, mockSubject.getSubjectId());
    session.close();

    assertEquals(mockSubject.getSubjectId(), actualSubject.getSubjectId());
    assertEquals(mockSubject.getFirstName(), actualSubject.getFirstName());
    assertEquals(mockSubject.getLastName(), actualSubject.getLastName());
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

  @Test(expected = SubjectDoesNotExistException.class)
  @Rollback
  public void updateSubjectShouldThrowExceptionIfSubjectDoesNotExist()
    throws SubjectDoesNotExistException {
    subjectDAO.updateSubject(123L, mockSubject);
  }

  @Test
  @Rollback
  public void updateSubjectShouldAlterExistingSubject()
    throws SubjectDoesNotExistException {
    Session session = sessionFactory.openSession();
    session.saveOrUpdate(mockSubject);
    session.close();

    mockSubject.setFirstName("John");
    mockSubject.setLastName("Blacksmith");
    mockSubject.getPersonalInformation().setMiddleName("Michel");
    mockSubject.getPersonalInformation().setDateOfBirth(null);
    mockSubject.getContactInformation().setEmail("k@s.com");
    mockSubject.getContactInformation().setTelephone("987654321");
    mockSubject.getContactInformation().setAddress(null);
    mockSubject.getEmployeeInformation().setEmployeeId("LKP");
    mockSubject.getEmployeeInformation().setNationalInsuranceNumber("682HG JK 8");
    mockSubject.getEmployeeInformation().setPosition("SQL Developer");
    mockSubject.getEmployeeInformation().setStartDate(null);
    mockSubject.getEmployeeInformation().setEndDate(null);
    subjectDAO.updateSubject(mockSubject.getSubjectId(), mockSubject);

    session = sessionFactory.openSession();
    final Subject actualSubject = session.get(Subject.class, mockSubject.getSubjectId());
    session.close();

    assertEquals(mockSubject.getSubjectId(), actualSubject.getSubjectId());
    assertEquals(mockSubject.getFirstName(), actualSubject.getFirstName());
    assertEquals(mockSubject.getLastName(), actualSubject.getLastName());
    assertEquals(mockSubject.getPersonalInformation().getMiddleName(),
      actualSubject.getPersonalInformation().getMiddleName());
    assertEquals(mockSubject.getPersonalInformation().getDateOfBirth(),
      actualSubject.getPersonalInformation().getDateOfBirth());
    assertEquals(mockSubject.getContactInformation().getTelephone(),
      actualSubject.getContactInformation().getTelephone());
    assertEquals(mockSubject.getContactInformation().getEmail(),
      actualSubject.getContactInformation().getEmail());
    assertNull(actualSubject.getContactInformation().getAddress());
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

  @Test(expected = SubjectDoesNotExistException.class)
  @Rollback
  public void updateSubjectPersonalInformationShouldThrowExceptionIfSubjectDoesNotExist()
    throws SubjectDoesNotExistException {
    subjectDAO.updateSubjectPersonalInformation(123L, mockPersonalInformation);
  }

  @Test
  @Rollback
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
  @Rollback
  public void updateSubjectContactInformationShouldThrowExceptionIfSubjectDoesNotExist()
    throws SubjectDoesNotExistException {
    subjectDAO.updateSubjectContactInformation(123L, mockContactInformation);
  }

  @Test
  @Rollback
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
  @Rollback
  public void updateSubjectEmployeeInformationShouldThrowExceptionIfSubjectDoesNotExist()
    throws SubjectDoesNotExistException {
    subjectDAO.updateSubjectEmployeeInformation(123L, mockEmployeeInformation);
  }

  @Test
  @Rollback
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
  @Rollback
  public void deleteSubjectShouldShouldThrowExceptionIfSubjectDoesNotExist() throws SubjectDoesNotExistException {
    subjectDAO.deleteSubject(678L);
  }

  @Test
  @Rollback
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

  @Test
  @Rollback
  public void getAllowanceShouldReturnAllowance() {
    final Session session = sessionFactory.openSession();
    session.saveOrUpdate(mockSubject);
    session.close();
    final long actualAllowance = subjectDAO.getAllowance(mockSubject.getSubjectId());

    assertEquals(mockHrInformation.getAllowance(), actualAllowance);
  }

  @Test
  @Rollback
  public void getUsedAllowanceShouldReturnUsedAllowance() {
    mockSubject.getHrInformation().setUsedAllowance(10L);
    final Session session = sessionFactory.openSession();
    session.saveOrUpdate(mockSubject);
    session.close();
    final long actualAllowance = subjectDAO.getUsedAllowance(mockSubject.getSubjectId());

    assertEquals(mockHrInformation.getUsedAllowance(), actualAllowance);
  }

}
