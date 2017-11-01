package org.openhr.dao.address;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.dao.subject.SubjectDAO;
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
public class AddressDAOTest {

  private final static Address mockAddress = new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL",
          "London", "UK");
  private final static Subject mockSubject = new Subject("John", null, "White",
          null, "Manager", "123456789", "john.white@cor.com", mockAddress);

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private AddressDAO addressDAO;

  @Test
  public void updateSubjectAddressShouldUpdateAddress() {
    
  }

}
