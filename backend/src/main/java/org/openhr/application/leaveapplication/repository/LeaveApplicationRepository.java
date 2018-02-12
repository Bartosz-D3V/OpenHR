package org.openhr.application.leaveapplication.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class LeaveApplicationRepository {
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public LeaveApplicationRepository(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<LeaveType> getLeaveTypes() throws HibernateException {
    List<LeaveType> leaveTypes;
    try {
      final Session session = sessionFactory.openSession();
      leaveTypes = session.createCriteria(LeaveType.class).list();
      session.close();
    } catch (final HibernateException hibernateException) {
      log.error(hibernateException.getLocalizedMessage());
      throw hibernateException;
    }

    return leaveTypes;
  }

}
