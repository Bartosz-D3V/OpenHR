package org.openhr.application.leaveapplication.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public class LeaveApplicationRepository {
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public LeaveApplicationRepository(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<LeaveType> getLeaveTypes() throws HibernateException {
    List<LeaveType> leaveTypes;
    try {
      final Session session = sessionFactory.getCurrentSession();
      leaveTypes = session.createCriteria(LeaveType.class).list();
    } catch (final HibernateException hibernateException) {
      log.error(hibernateException.getLocalizedMessage());
      throw hibernateException;
    }

    return leaveTypes;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public boolean dateRangeAlreadyBooked(final long subjectId, final LocalDate startDate, final LocalDate endDate) {
    final int numOfResults;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(LeaveApplication.class);
      numOfResults = criteria
        .createAlias("subject", "subject")
        .add(Restrictions.eq("subject.subjectId", subjectId))
        .add(Restrictions.eq("approvedByManager", true))
        .add(Restrictions.eq("approvedByHR", true))
        .add(Restrictions.conjunction()
          .add(Restrictions.ge("endDate", startDate))
          .add(Restrictions.le("startDate", endDate)))
        .setMaxResults(1)
        .setReadOnly(true)
        .setCacheable(true)
        .list()
        .size();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return numOfResults > 0;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<LeaveApplication> getAwaitingForManagerLeaveApplications(final long subjectId) {
    List<LeaveApplication> filteredLeaveApplications;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(LeaveApplication.class);
      filteredLeaveApplications = criteria
        .createAlias("subject", "subject")
        .add(Restrictions.eq("approvedByManager", false))
        .add(Restrictions.eq("approvedByHR", false))
        .add(Restrictions.eq("subject.subjectId", subjectId))
        .setReadOnly(true)
        .list();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return filteredLeaveApplications;
  }
}
