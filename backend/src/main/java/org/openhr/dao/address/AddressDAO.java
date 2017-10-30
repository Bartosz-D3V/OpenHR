package org.openhr.dao.address;

import org.hibernate.HibernateException;
import org.openhr.domain.address.Address;

public interface AddressDAO {
  void updateSubjectAddress(long subjectId, Address address);

  Address getSubjectAddress(long subjectId) throws HibernateException;
}
