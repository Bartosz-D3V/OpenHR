package org.openhr.dao.subject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.domain.address.Address;
import org.openhr.domain.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SubjectDAOTest {

  private final static Address mockAddress = new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL",
          "London", "UK");
  private final static Subject mockSubject = new Subject("John", null, "White",
          null, "Manager", "123456789", "john.white@cor.com", mockAddress);

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
    assertEquals(mockSubject.getMiddleName(), actualSubject.getMiddleName());
    assertEquals(mockSubject.getLastName(), actualSubject.getLastName());
    assertEquals(mockSubject.getDateOfBirth(), actualSubject.getDateOfBirth());
    assertEquals(mockSubject.getPosition(), actualSubject.getPosition());
    assertEquals(mockSubject.getTelephone(), actualSubject.getTelephone());
    assertEquals(mockSubject.getEmail(), actualSubject.getEmail());
    assertEquals(mockSubject.getAddress().getFirstLineAddress(), actualSubject.getAddress().getFirstLineAddress());
    assertEquals(mockSubject.getAddress().getSecondLineAddress(), actualSubject.getAddress().getSecondLineAddress());
    assertEquals(mockSubject.getAddress().getThirdLineAddress(), actualSubject.getAddress().getThirdLineAddress());
    assertEquals(mockSubject.getAddress().getCity(), actualSubject.getAddress().getCity());
    assertEquals(mockSubject.getAddress().getPostcode(), actualSubject.getAddress().getPostcode());
    assertEquals(mockSubject.getAddress().getCountry(), actualSubject.getAddress().getCountry());
  }

  @Test
  public void addSubjectShouldInsertSubjectToDatabase() {
    subjectDAO.addSubject(mockSubject);
    Session session = sessionFactory.openSession();
    Subject actualSubject = session.get(Subject.class, 1L);
    session.close();

    assertEquals(mockSubject.getSubjectId(), actualSubject.getSubjectId());
    assertEquals(mockSubject.getFirstName(), actualSubject.getFirstName());
    assertEquals(mockSubject.getMiddleName(), actualSubject.getMiddleName());
    assertEquals(mockSubject.getLastName(), actualSubject.getLastName());
    assertEquals(mockSubject.getDateOfBirth(), actualSubject.getDateOfBirth());
    assertEquals(mockSubject.getPosition(), actualSubject.getPosition());
    assertEquals(mockSubject.getTelephone(), actualSubject.getTelephone());
    assertEquals(mockSubject.getEmail(), actualSubject.getEmail());
    assertEquals(mockSubject.getAddress().getFirstLineAddress(), actualSubject.getAddress().getFirstLineAddress());
    assertEquals(mockSubject.getAddress().getSecondLineAddress(), actualSubject.getAddress().getSecondLineAddress());
    assertEquals(mockSubject.getAddress().getThirdLineAddress(), actualSubject.getAddress().getThirdLineAddress());
    assertEquals(mockSubject.getAddress().getCity(), actualSubject.getAddress().getCity());
    assertEquals(mockSubject.getAddress().getPostcode(), actualSubject.getAddress().getPostcode());
    assertEquals(mockSubject.getAddress().getCountry(), actualSubject.getAddress().getCountry());
  }

  @Test
  public void updateSubjectShouldAlterExistingSubjectInDatabase() {
    long subjectReference;
    Subject actualSubject;

    Session sessionPut = sessionFactory.openSession();
    subjectReference = (long) sessionPut.save(mockSubject);
    sessionPut.close();

    mockSubject.setFirstName("John");
    mockSubject.setMiddleName("Michel");
    mockSubject.setLastName("Xavier");
    mockSubject.setDateOfBirth(null);
    mockSubject.setPosition("Senior Java Developer");
    mockSubject.setTelephone("987654321");
    mockSubject.setEmail("j.m.x@mail.com");
    this.subjectDAO.updateSubject(mockSubject);

    Session sessionGet = sessionFactory.openSession();
    actualSubject = sessionGet.get(Subject.class, subjectReference);
    sessionGet.close();

    assertEquals(mockSubject.getSubjectId(), actualSubject.getSubjectId());
    assertEquals(mockSubject.getFirstName(), actualSubject.getFirstName());
    assertEquals(mockSubject.getMiddleName(), actualSubject.getMiddleName());
    assertEquals(mockSubject.getLastName(), actualSubject.getLastName());
    assertEquals(mockSubject.getDateOfBirth(), actualSubject.getDateOfBirth());
    assertEquals(mockSubject.getPosition(), actualSubject.getPosition());
    assertEquals(mockSubject.getTelephone(), actualSubject.getTelephone());
    assertEquals(mockSubject.getEmail(), actualSubject.getEmail());
  }

  @Test(expected = SubjectDoesNotExistException.class)
  public void updateSubjectAddressShouldThrowExceptionIfSubjectDoesNotExist() throws SubjectDoesNotExistException {
    subjectDAO.updateSubjectAddress(123L, mockAddress);
  }

  @Test
  public void updateSubjectAddressShouldUpdateAddressBySubjectIdAndNewAddress() throws SubjectDoesNotExistException {
    mockAddress.setFirstLineAddress("Sample street");
    mockAddress.setSecondLineAddress("Sample street 2nd line");
    mockAddress.setThirdLineAddress("Sample street 3rd line");
    mockAddress.setPostcode("89-123");
    mockAddress.setCity("Warsaw");
    mockAddress.setCountry("Poland");

    Session session = sessionFactory.openSession();
    final long subjectReference = (long) session.save(mockSubject);
    Subject subject = session.get(Subject.class, subjectReference);
    session.close();

    subjectDAO.updateSubjectAddress(subjectReference, mockAddress);

    assertEquals(mockSubject.getAddress().getFirstLineAddress(), subject.getAddress().getFirstLineAddress());
    assertEquals(mockSubject.getAddress().getSecondLineAddress(), subject.getAddress().getSecondLineAddress());
    assertEquals(mockSubject.getAddress().getThirdLineAddress(), subject.getAddress().getThirdLineAddress());
    assertEquals(mockSubject.getAddress().getCity(), subject.getAddress().getCity());
    assertEquals(mockSubject.getAddress().getPostcode(), subject.getAddress().getPostcode());
    assertEquals(mockSubject.getAddress().getCountry(), subject.getAddress().getCountry());
  }

}
