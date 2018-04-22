package org.openhr.application.delegation.repository;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openhr.application.delegation.dao.DelegationApplicationDAO;
import org.openhr.application.delegation.domain.DelegationApplication;
import org.openhr.common.domain.country.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DelegationApplicationRepository {
  private final DelegationApplicationDAO delegationApplicationDAO;
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final SessionFactory sessionFactory;

  public DelegationApplicationRepository(
      final DelegationApplicationDAO delegationApplicationDAO,
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
      countries = criteria.setReadOnly(true).setCacheable(true).list();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    return countries;
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public DelegationApplication createDelegationApplication(
      final DelegationApplication delegationApplication) {
    return delegationApplicationDAO.createDelegationApplication(delegationApplication);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public DelegationApplication getDelegationApplication(final long delegationApplicationId) {
    return delegationApplicationDAO.getDelegationApplication(delegationApplicationId);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public DelegationApplication updateDelegationApplication(
      final DelegationApplication delegationApplication) {
    return delegationApplicationDAO.updateDelegationApplication(delegationApplication);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<DelegationApplication> getSubjectsDelegationApplications(final long subjectId) {
    List<DelegationApplication> leaveApplications;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(DelegationApplication.class);
      leaveApplications =
          criteria
              .createAlias("subject", "subject")
              .add(Restrictions.eq("subject.subjectId", subjectId))
              .setReadOnly(true)
              .list();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return leaveApplications;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<DelegationApplication> getAwaitingForActionDelegationApplications(
      final long subjectId) {
    List<DelegationApplication> filteredLeaveApplications;
    try {
      final Session session = sessionFactory.getCurrentSession();
      filteredLeaveApplications =
          session
              .createCriteria(DelegationApplication.class)
              .createAlias("assignee", "assignee")
              .add(
                  Restrictions.conjunction()
                      .add(Restrictions.eq("terminated", false))
                      .add(Restrictions.eq("assignee.subjectId", subjectId)))
              .setReadOnly(true)
              .list();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return filteredLeaveApplications;
  }
}
