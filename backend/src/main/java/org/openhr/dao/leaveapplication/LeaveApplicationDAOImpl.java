package org.openhr.dao.leaveapplication;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.subject.Subject;
import org.openhr.exception.ApplicationDoesNotExistException;
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
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LeaveApplication getLeaveApplication(final long applicationId) throws ApplicationDoesNotExistException {
    LeaveApplication leaveApplication;
    try {
      Session session = sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      leaveApplication = session.get(LeaveApplication.class, applicationId);
      transaction.commit();
      session.close();
    } catch (final HibernateException hibernateException) {
      log.error(hibernateException.getMessage());
      throw hibernateException;
    }

    if (leaveApplication == null) {
      log.error("Application could not be found");
      throw new ApplicationDoesNotExistException("Application could not be found");
    }

    return leaveApplication;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public LeaveApplication createLeaveApplication(final Subject subject, final LeaveApplication leaveApplication)
    throws HibernateException {
    leaveApplication.setSubject(subject);
    subject.addLeaveApplication(leaveApplication);
    try {
      Session session = sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      session.merge(subject);
      transaction.commit();
      session.close();
    } catch (final HibernateException hibernateException) {
      log.error(hibernateException.getMessage());
      throw hibernateException;
    }

    return leaveApplication;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public LeaveApplication updateLeaveApplication(final LeaveApplication leaveApplication)
    throws ApplicationDoesNotExistException, HibernateException {
    final LeaveApplication legacyLeaveApplication = getLeaveApplication(leaveApplication.getApplicationId());
    legacyLeaveApplication.setStartDate(leaveApplication.getStartDate());
    legacyLeaveApplication.setEndDate(leaveApplication.getEndDate());
    legacyLeaveApplication.setMessage(leaveApplication.getMessage());
    legacyLeaveApplication.setLeaveType(leaveApplication.getLeaveType());
    legacyLeaveApplication.setApprovedByManager(leaveApplication.isApprovedByManager());
    legacyLeaveApplication.setApprovedByHR(leaveApplication.isApprovedByHR());
    try {
      Session session = sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      session.merge(legacyLeaveApplication);
      transaction.commit();
      session.close();
    } catch (final HibernateException hibernateException) {
      log.error(hibernateException.getMessage());
      throw hibernateException;
    }

    return legacyLeaveApplication;
  }
}
