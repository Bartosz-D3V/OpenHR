package org.openhr.application.leaveapplication.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.common.dao.BaseDAO;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LeaveApplicationDAOImpl extends BaseDAO implements LeaveApplicationDAO {
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public LeaveApplicationDAOImpl(final SessionFactory sessionFactory) {
    super(sessionFactory);
    this.sessionFactory = sessionFactory;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LeaveApplication getLeaveApplication(final long applicationId) throws ApplicationDoesNotExistException {
    final LeaveApplication leaveApplication = (LeaveApplication) super.get(LeaveApplication.class, applicationId);

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
    super.save(leaveApplication);

    return leaveApplication;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public LeaveApplication updateLeaveApplication(final LeaveApplication leaveApplication)
    throws ApplicationDoesNotExistException, HibernateException {
    final LeaveApplication legacyLeaveApplication = getLeaveApplication(leaveApplication.getApplicationId());
    legacyLeaveApplication.setStartDate(leaveApplication.getStartDate());
    legacyLeaveApplication.setEndDate(leaveApplication.getEndDate());
    legacyLeaveApplication.setMessage(leaveApplication.getMessage());
    legacyLeaveApplication.setLeaveType(leaveApplication.getLeaveType());
    legacyLeaveApplication.setApprovedByManager(leaveApplication.isApprovedByManager());
    legacyLeaveApplication.setApprovedByHR(leaveApplication.isApprovedByHR());
    legacyLeaveApplication.setProcessInstanceId(leaveApplication.getProcessInstanceId());
    super.merge(legacyLeaveApplication);

    return legacyLeaveApplication;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long getLeaveApplicationIdByProcessId(final String processInstanceId) {
    long applicationId;
    try {
      final Session session = sessionFactory.getCurrentSession();
      applicationId = (long) session.createCriteria(LeaveApplication.class)
        .add(Restrictions.eq("processInstanceId", processInstanceId))
        .setProjection(Projections.property("applicationId"))
        .setCacheable(true)
        .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return applicationId;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getApplicationApplicant(final long applicationId) {
    Subject applicant = null;
    try {
      final Session session = sessionFactory.getCurrentSession();
      applicant = (Subject) session.createCriteria(LeaveApplication.class)
        .add(Restrictions.eq("applicationId", applicationId))
        .setProjection(Projections.property("subject"))
        .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
    }

    return applicant;
  }
}
