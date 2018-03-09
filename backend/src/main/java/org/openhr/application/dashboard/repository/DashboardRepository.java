package org.openhr.application.dashboard.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.openhr.application.dashboard.dto.MonthSummaryDTO;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
      monthlyReport = criteria.createAlias("subject", "subject")
        .add(Restrictions.conjunction()
          .add(Restrictions.eq("subject.subjectId", subjectId))
          .add(Restrictions.eq("terminated", true))
          .add(Restrictions.eq("approvedByHR", true))
          .add(Restrictions.between("startDate", LocalDate.of(1, 1, LocalDate.now().getYear()),
            LocalDate.of(31, 12, LocalDate.now().getYear()))))
        .setProjection(Projections.groupProperty("startDate"))
        .setReadOnly(true)
        .setCacheable(true)
        .list();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return monthlyReport;
  }

}
