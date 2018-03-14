package org.openhr.application.delegationapplication.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openhr.application.delegationapplication.dao.DelegationApplicationDAO;
import org.openhr.application.delegationapplication.domain.DelegationApplication;
import org.openhr.common.domain.country.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DelegationApplicationRepository {
  private final DelegationApplicationDAO delegationApplicationDAO;
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final SessionFactory sessionFactory;

  public DelegationApplicationRepository(final DelegationApplicationDAO delegationApplicationDAO,
                                         final SessionFactory sessionFactory) {
    this.delegationApplicationDAO = delegationApplicationDAO;
    this.sessionFactory = sessionFactory;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<Country> getCountries() {
    List<Country> countries;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(Country.class);
      countries = criteria
        .setReadOnly(true)
        .setCacheable(true)
        .list();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    return countries;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public DelegationApplication createDelegationApplication(final DelegationApplication delegationApplication) {
    return delegationApplicationDAO.createDelegationApplication(delegationApplication);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public DelegationApplication updateDelegationApplication(final DelegationApplication delegationApplication) {
    return delegationApplicationDAO.updateDelegationApplication(delegationApplication);
  }
}
