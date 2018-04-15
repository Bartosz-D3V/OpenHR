package org.openhr.application.dashboard.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openhr.application.delegation.domain.DelegationApplication;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.common.domain.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.SUPPORTS)
public class DashboardRepository {
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public DashboardRepository(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<LeaveApplication> getApplicationsInCurrentYear(final long subjectId) {
    final List<LeaveApplication> monthlyReport;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(LeaveApplication.class);
      monthlyReport =
          criteria
              .createAlias("subject", "subject")
              .add(
                  Restrictions.conjunction()
                      .add(Restrictions.eq("subject.subjectId", subjectId))
                      .add(Restrictions.eq("terminated", true))
                      .add(Restrictions.eq("approvedByHR", true))
                      .add(
                          Restrictions.between(
                              "startDate",
                              LocalDate.of(LocalDate.now().getYear(), 1, 1),
                              LocalDate.of(LocalDate.now().getYear(), 12, 31))))
              .setReadOnly(true)
              .list();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    return monthlyReport;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<LeaveApplication> getCurrentYearStatusRatio() {
    final List<LeaveApplication> monthlyReport;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(LeaveApplication.class);
      monthlyReport =
          criteria
              .add(
                  Restrictions.conjunction()
                      .add(Restrictions.eq("terminated", true))
                      .add(
                          Restrictions.between(
                              "startDate",
                              LocalDate.of(LocalDate.now().getYear(), 1, 1),
                              LocalDate.of(LocalDate.now().getYear(), 12, 31))))
              .setProjection(Projections.groupProperty("startDate"))
              .setReadOnly(true)
              .list();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    return monthlyReport;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public BigDecimal getDelegationExpenditures(final int year) {
    BigDecimal totalExpenditures;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(DelegationApplication.class);
      totalExpenditures =
          (BigDecimal)
              criteria
                  .add(Restrictions.eq("approvedByHR", true))
                  .add(Restrictions.gt("startDate", LocalDate.of(year, 1, 1)))
                  .add(Restrictions.le("startDate", LocalDate.of(year, 12, 31)))
                  .setProjection(Projections.sum("budget"))
                  .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    return totalExpenditures;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<Subject> retrieveSubjectsOnLeaveToday() {
    final List<Subject> subjects;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(LeaveApplication.class);
      subjects =
          criteria
              .add(Restrictions.ge("endDate", LocalDate.now()))
              .add(Restrictions.le("startDate", LocalDate.now()))
              .add(Restrictions.eq("terminated", true))
              .add(Restrictions.eq("approvedByHR", true))
              .setProjection(Projections.property("subject"))
              .setReadOnly(true)
              .setCacheable(true)
              .list();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    return subjects;
  }
}
