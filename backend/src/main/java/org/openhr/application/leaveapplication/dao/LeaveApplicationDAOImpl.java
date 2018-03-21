package org.openhr.application.leaveapplication.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.common.dao.BaseDAO;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
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
  @Transactional(propagation = Propagation.REQUIRED)
  public LeaveApplication updateLeaveApplication(final LeaveApplication leaveApplication)
    throws ApplicationDoesNotExistException, HibernateException {
    final LeaveApplication savedLeaveApplication = getLeaveApplication(leaveApplication.getApplicationId());
    BeanUtils.copyProperties(leaveApplication, savedLeaveApplication, "subject", "assignee");
    super.merge(savedLeaveApplication);

    return savedLeaveApplication;
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
        .setReadOnly(true)
        .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
    }

    return applicant;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getApplicationAssignee(long applicationId) {
    Subject assignee = null;
    try {
      final Session session = sessionFactory.getCurrentSession();
      assignee = (Subject) session.createCriteria(LeaveApplication.class)
        .add(Restrictions.eq("applicationId", applicationId))
        .setProjection(Projections.property("assignee"))
        .setReadOnly(true)
        .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
    }

    return assignee;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<LeaveType> getLeaveTypes() {
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
}
