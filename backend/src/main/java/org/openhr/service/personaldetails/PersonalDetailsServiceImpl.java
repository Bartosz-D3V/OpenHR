package org.openhr.service.personaldetails;

import org.hibernate.HibernateException;
import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.dao.address.AddressDAO;
import org.openhr.dao.subject.SubjectDAO;
import org.openhr.domain.address.Address;
import org.openhr.domain.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonalDetailsServiceImpl implements PersonalDetailsService {

  private final SubjectDAO subjectDAO;
  private final AddressDAO addressDAO;

  public PersonalDetailsServiceImpl(final SubjectDAO subjectDAO, final AddressDAO addressDAO) {
    this.subjectDAO = subjectDAO;
    this.addressDAO = addressDAO;
  }

  @Override
  @Transactional(readOnly = true)
  public Subject getSubjectDetails(final long subjectId) throws SubjectDoesNotExistException {
    return this.subjectDAO.getSubjectDetails(subjectId);
  }

  @Override
  @Transactional
  public void addSubject(final Subject subject) throws HibernateException {
    this.subjectDAO.addSubject(subject);
  }

  @Override
  @Transactional
  public void updateSubjectAddress(final long subjectId, final Address address) throws HibernateException {
    this.addressDAO.updateSubjectAddress(subjectId, address);
  }
}
