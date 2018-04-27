package org.openhr.application.leaveapplication.dao;

import java.util.List;
import java.util.Locale;
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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class LeaveApplicationDAOImpl extends BaseDAO implements LeaveApplicationDAO {
  private final SessionFactory sessionFactory;
  private final MessageSource messageSource;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public LeaveApplicationDAOImpl(
      final SessionFactory sessionFactory, final MessageSource messageSource) {
    super(sessionFactory);
    this.sessionFactory = sessionFactory;
    this.messageSource = messageSource;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LeaveApplication getLeaveApplication(final long leaveApplicationId)
      throws ApplicationDoesNotExistException {
    final LeaveApplication leaveApplication =
        (LeaveApplication) super.get(LeaveApplication.class, leaveApplicationId);

    if (leaveApplication == null) {
      log.error(
          messageSource.getMessage("error.applicationdoesnotexist", null, Locale.getDefault()));
      throw new ApplicationDoesNotExistException(
          messageSource.getMessage("error.applicationdoesnotexist", null, Locale.getDefault()));
    }

    return leaveApplication;
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public LeaveApplication createLeaveApplication(
      final Subject subject, final LeaveApplication leaveApplication) throws HibernateException {
    leaveApplication.setSubject(subject);
    super.save(leaveApplication);

    return leaveApplication;
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public LeaveApplication updateLeaveApplication(
      final long leaveApplicationId, final LeaveApplication leaveApplication)
      throws ApplicationDoesNotExistException, HibernateException {
    final LeaveApplication savedLeaveApplication = getLeaveApplication(leaveApplicationId);
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
      applicationId =
          (long)
              session
                  .createCriteria(LeaveApplication.class)
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
      applicant =
          (Subject)
              session
                  .createCriteria(LeaveApplication.class)
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
      assignee =
          (Subject)
              session
                  .createCriteria(LeaveApplication.class)
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
