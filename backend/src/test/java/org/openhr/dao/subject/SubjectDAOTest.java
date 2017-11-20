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

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SubjectDAOTest {

  private final static Address mockAddress = new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL",
    "London", "UK");
  private final static PersonalInformation mockPersonalInformation = new PersonalInformation("John", null, "Tester");
  private final static ContactInformation mockContactInformation = new ContactInformation("0123456789", "j.x@g.com", mockAddress);
  private final static EmployeeInformation mockEmployeeInformation = new EmployeeInformation("S8821 B", "12A", null, null);
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
    long subjectReference;
    Session session = sessionFactory.openSession();
    subjectReference = (long) session.save(mockSubject);
    session.close();
    Subject actualSubject = subjectDAO.getSubjectDetails(subjectReference);

    assertEquals(mockSubject.getSubjectId(), actualSubject.getSubjectId());
    assertEquals(mockSubject.getFirstName(), actualSubject.getFirstName());
    assertEquals(mockSubject.getLastName(), actualSubject.getLastName());
    assertEquals(mockSubject.getRole(), actualSubject.getRole());
    assertEquals(mockSubject.getPersonalInformation().getMiddleName(),
      actualSubject.getPersonalInformation().getMiddleName());
    assertEquals(mockSubject.getPersonalInformation().getDateOfBirth(),
      actualSubject.getPersonalInformation().getDateOfBirth());
    assertEquals(mockSubject.getPersonalInformation().getPosition(),
      actualSubject.getPersonalInformation().getPosition());
    assertEquals(mockSubject.getPersonalInformation().getSubject().getSubjectId(),
      actualSubject.getPersonalInformation().getSubject().getSubjectId());
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
    assertEquals(mockSubject.getContactInformation().getSubject().getSubjectId(),
      actualSubject.getContactInformation().getSubject().getSubjectId());
    assertEquals(mockSubject.getEmployeeInformation().getEmployeeId(),
      actualSubject.getEmployeeInformation().getEmployeeId());
    assertEquals(mockSubject.getEmployeeInformation().getNationalInsuranceNumber(),
      actualSubject.getEmployeeInformation().getNationalInsuranceNumber());
    assertEquals(mockSubject.getEmployeeInformation().getStartDate(),
      actualSubject.getEmployeeInformation().getStartDate());
    assertEquals(mockSubject.getEmployeeInformation().getEndDate(),
      actualSubject.getEmployeeInformation().getEndDate());
    assertEquals(mockSubject.getEmployeeInformation().getSubject().getSubjectId(),
      actualSubject.getEmployeeInformation().getSubject().getSubjectId());
  }

  @Test
  public void addSubjectShouldInsertSubjectToDatabase() {
    subjectDAO.addSubject(mockSubject);
    Session session = sessionFactory.openSession();
    Subject actualSubject = session.get(Subject.class, 1L);
    session.close();

    assertEquals(mockSubject.getSubjectId(), actualSubject.getSubjectId());
    assertEquals(mockSubject.getFirstName(), actualSubject.getFirstName());
    assertEquals(mockSubject.getLastName(), actualSubject.getLastName());
    assertEquals(mockSubject.getRole(), actualSubject.getRole());
    assertEquals(mockSubject.getPersonalInformation().getMiddleName(),
      actualSubject.getPersonalInformation().getMiddleName());
    assertEquals(mockSubject.getPersonalInformation().getDateOfBirth(),
      actualSubject.getPersonalInformation().getDateOfBirth());
    assertEquals(mockSubject.getPersonalInformation().getPosition(),
      actualSubject.getPersonalInformation().getPosition());
    assertEquals(mockSubject.getPersonalInformation().getSubject().getSubjectId(),
      actualSubject.getPersonalInformation().getSubject().getSubjectId());
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
    assertEquals(mockSubject.getContactInformation().getSubject().getSubjectId(),
      actualSubject.getContactInformation().getSubject().getSubjectId());
    assertEquals(mockSubject.getEmployeeInformation().getEmployeeId(),
      actualSubject.getEmployeeInformation().getEmployeeId());
    assertEquals(mockSubject.getEmployeeInformation().getNationalInsuranceNumber(),
      actualSubject.getEmployeeInformation().getNationalInsuranceNumber());
    assertEquals(mockSubject.getEmployeeInformation().getStartDate(),
      actualSubject.getEmployeeInformation().getStartDate());
    assertEquals(mockSubject.getEmployeeInformation().getEndDate(),
      actualSubject.getEmployeeInformation().getEndDate());
    assertEquals(mockSubject.getEmployeeInformation().getSubject().getSubjectId(),
      actualSubject.getEmployeeInformation().getSubject().getSubjectId());
  }

  @Test
  public void updateSubjectPersonalInformationShouldAlterExistingPersonalInformationInDatabase() {
    long subjectReference;
    Subject actualSubject;

    Session sessionPut = sessionFactory.openSession();
    subjectReference = (long) sessionPut.save(mockSubject);
    sessionPut.close();

    mockSubject.getPersonalInformation().setMiddleName("Michel");
    mockSubject.getPersonalInformation().setDateOfBirth(null);
    mockSubject.getPersonalInformation().setPosition("Senior Java Developer");
    this.subjectDAO.updateSubject(mockSubject);

    Session sessionGet = sessionFactory.openSession();
    actualSubject = sessionGet.get(Subject.class, subjectReference);
    sessionGet.close();

    assertEquals(mockSubject.getPersonalInformation().getSubject().getSubjectId(), actualSubject.getSubjectId());
    assertEquals(mockSubject.getPersonalInformation().getMiddleName(),
      actualSubject.getPersonalInformation().getMiddleName());
    assertEquals(mockSubject.getPersonalInformation().getDateOfBirth(),
      actualSubject.getPersonalInformation().getDateOfBirth());
    assertEquals(mockSubject.getPersonalInformation().getPosition(),
      actualSubject.getPersonalInformation().getPosition());
  }

  @Test(expected = SubjectDoesNotExistException.class)
  public void updateSubjectPersonalInformationShouldThrowExceptionIfSubjectDoesNotExist() throws SubjectDoesNotExistException {
    subjectDAO.updateSubjectPersonalInformation(123L, mockPersonalInformation);
  }

//  @Test
//  public void updateSubjectPersonalInformationShouldUpdateAddressBySubjectIdAndNewAddress() throws SubjectDoesNotExistException {
//    mockAddress.setFirstLineAddress("Sample street");
//    mockAddress.setSecondLineAddress("Sample street 2nd line");
//    mockAddress.setThirdLineAddress("Sample street 3rd line");
//    mockAddress.setPostcode("89-123");
//    mockAddress.setCity("Warsaw");
//    mockAddress.setCountry("Poland");
//
//    Session session = sessionFactory.openSession();
//    final long subjectReference = (long) session.save(mockSubject);
//    Subject subject = session.get(Subject.class, subjectReference);
//    session.close();
//
//    subjectDAO.updateSubjectAddress(subjectReference, mockAddress);
//
//    assertEquals(mockSubject.getAddress().getFirstLineAddress(), subject.getAddress().getFirstLineAddress());
//    assertEquals(mockSubject.getAddress().getSecondLineAddress(), subject.getAddress().getSecondLineAddress());
//    assertEquals(mockSubject.getAddress().getThirdLineAddress(), subject.getAddress().getThirdLineAddress());
//    assertEquals(mockSubject.getAddress().getCity(), subject.getAddress().getCity());
//    assertEquals(mockSubject.getAddress().getPostcode(), subject.getAddress().getPostcode());
//    assertEquals(mockSubject.getAddress().getCountry(), subject.getAddress().getCountry());
//  }

}
