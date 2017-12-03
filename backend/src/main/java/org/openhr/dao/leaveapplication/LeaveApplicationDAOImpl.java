package org.openhr.dao.leaveapplication;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.enumeration.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LeaveApplicationDAOImpl implements LeaveApplicationDAO {

  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public LeaveApplicationDAOImpl(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public void createLeaveApplication(final LeaveApplication leaveApplication) throws HibernateException {
    try {
      Session session = sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      session.save(leaveApplication);
      transaction.commit();
      session.close();
    } catch (final HibernateException hibernateException) {
      log.error(hibernateException.getMessage());
      throw hibernateException;
    }
  }

  @Override
  public void rejectLeaveApplication(Role role, long applicationId) {

  }

  @Override
  public void approveLeaveApplication(Role role, long applicationId) {

  }
}
