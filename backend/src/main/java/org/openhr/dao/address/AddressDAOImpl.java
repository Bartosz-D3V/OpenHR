package org.openhr.dao.address;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.openhr.domain.address.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AddressDAOImpl implements AddressDAO {

  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public AddressDAOImpl(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  @Transactional
  public void updateSubjectAddress(final long subjectId, final Address address) throws HibernateException {
    try {
      Session session = this.sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      final Address oldAddress = this.getSubjectAddress(subjectId);
      oldAddress.setFirstLineAddress(address.getFirstLineAddress());
      oldAddress.setSecondLineAddress(address.getSecondLineAddress());
      oldAddress.setThirdLineAddress(address.getThirdLineAddress());
      oldAddress.setPostcode(address.getPostcode());
      oldAddress.setCity(address.getCity());
      oldAddress.setCountry(address.getCountry());
      session.merge(oldAddress);
      transaction.commit();
      session.close();
    } catch (HibernateException hibernateException) {
      this.log.error(hibernateException.getMessage());
      throw hibernateException;
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Address getSubjectAddress(final long subjectId) throws HibernateException {
    Address address;
    try {
      Session session = this.sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      address = session.get(Address.class, subjectId);
      transaction.commit();
      session.close();
    } catch (HibernateException hibernateException) {
      log.error(hibernateException.getMessage());
      throw hibernateException;
    }

    return address;
  }

}
