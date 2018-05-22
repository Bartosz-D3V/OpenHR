package org.openhr.application.allowance.repository;

import java.util.Locale;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AllowanceRepository {
  private final SessionFactory sessionFactory;
  private final MessageSource messageSource;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public AllowanceRepository(
      final MessageSource messageSource, final SessionFactory sessionFactory) {
    this.messageSource = messageSource;
    this.sessionFactory = sessionFactory;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long getAllowance(final long subjectId) {
    long allowance;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(Subject.class);
      allowance =
          (long)
              criteria
                  .createAlias("hrInformation", "hrInformation")
                  .add(Restrictions.eq("subjectId", subjectId))
                  .setProjection(Projections.property("hrInformation.allowance"))
                  .uniqueResult();
      session.flush();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    return allowance;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long getUsedAllowance(final long subjectId) {
    long usedAllowance;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(Subject.class);
      usedAllowance =
          (long)
              criteria
                  .createAlias("hrInformation", "hrInformation")
                  .add(Restrictions.eq("subjectId", subjectId))
                  .setProjection(Projections.property("hrInformation.usedAllowance"))
                  .uniqueResult();
      session.flush();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    return usedAllowance;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void resetAllowance(final long numberOfDaysToCarryOver) {
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(HrInformation.class);
      final ScrollableResults scrollableResults = criteria.scroll();
      while (scrollableResults.next()) {
        final HrInformation hrInformation = (HrInformation) scrollableResults.get(0);
        final long allowance = hrInformation.getAllowance();
        final long usedAllowance = hrInformation.getUsedAllowance();
        if (usedAllowance < allowance) {
          final long diff = (allowance - usedAllowance);
          final long daysToCarry = diff > numberOfDaysToCarryOver ? numberOfDaysToCarryOver : diff;
          hrInformation.setAllowance(allowance + daysToCarry);
        }
        hrInformation.setUsedAllowance(0);
        session.merge(hrInformation);
      }
      session.flush();
      log.info(
          messageSource.getMessage("allowance.reset.all.successful", null, Locale.getDefault()));
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
  }
}
