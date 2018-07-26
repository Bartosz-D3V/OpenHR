package org.openhr.application.leaveapplication.repository;

import java.time.LocalDate;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openhr.application.leaveapplication.dao.LeaveApplicationDAO;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.util.iterable.LocalDateRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class LeaveApplicationRepository {

  private final LeaveApplicationDAO leaveApplicationDAO;
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public LeaveApplicationRepository(
      final LeaveApplicationDAO leaveApplicationDAO, final SessionFactory sessionFactory) {
    this.leaveApplicationDAO = leaveApplicationDAO;
    this.sessionFactory = sessionFactory;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public boolean dateRangeAlreadyBooked(
      final long subjectId, final LocalDate startDate, final LocalDate endDate) {
    final int numOfResults;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(LeaveApplication.class);
      numOfResults =
          criteria
              .createAlias("subject", "subject")
              .add(Restrictions.eq("subject.subjectId", subjectId))
              .add(Restrictions.eq("approvedByManager", true))
              .add(Restrictions.eq("approvedByHR", true))
              .add(
                  Restrictions.conjunction()
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
  public List<LeaveApplication> getAwaitingForActionLeaveApplications(final long subjectId) {
    List<LeaveApplication> filteredLeaveApplications;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(LeaveApplication.class);
      filteredLeaveApplications =
          criteria
              .createAlias("assignee", "assignee")
              .add(Restrictions.eq("terminated", false))
              .add(Restrictions.eq("assignee.subjectId", subjectId))
              .setReadOnly(true)
              .list();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return filteredLeaveApplications;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LeaveType getLeaveType(final long leaveTypeId) {
    LeaveType leaveType;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(LeaveType.class);
      leaveType =
          (LeaveType)
              criteria
                  .add(Restrictions.eq("leaveTypeId", leaveTypeId))
                  .setReadOnly(true)
                  .setCacheable(true)
                  .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return leaveType;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LeaveApplication getLeaveApplication(final long leaveApplicationId)
      throws ApplicationDoesNotExistException {
    return leaveApplicationDAO.getLeaveApplication(leaveApplicationId);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<LeaveApplication> getSubjectsLeaveApplications(final long subjectId) {
    List<LeaveApplication> leaveApplications;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(LeaveApplication.class);
      leaveApplications =
          criteria
              .createAlias("subject", "subject")
              .add(Restrictions.eq("subject.subjectId", subjectId))
              .setReadOnly(true)
              .list();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return leaveApplications;
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public LeaveApplication createLeaveApplication(
      final Subject subject, final LeaveApplication leaveApplication) {
    return leaveApplicationDAO.createLeaveApplication(subject, leaveApplication);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public LeaveApplication updateLeaveApplication(
      final long leaveApplicationId, final LeaveApplication leaveApplication)
      throws ApplicationDoesNotExistException {
    return leaveApplicationDAO.updateLeaveApplication(leaveApplicationId, leaveApplication);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getApplicationApplicant(final long applicationId) {
    return leaveApplicationDAO.getApplicationApplicant(applicationId);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getApplicationAssignee(final long applicationId) {
    return leaveApplicationDAO.getApplicationAssignee(applicationId);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long getLeaveApplicationIdByProcessId(final String processInstanceId) {
    return leaveApplicationDAO.getLeaveApplicationIdByProcessId(processInstanceId);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<LeaveType> getLeaveTypes() throws HibernateException {
    return leaveApplicationDAO.getLeaveTypes();
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<LeaveApplication> getLeaveApplicationsInRange(
      final LocalDateRange dateRange, final long subjectId) {
    List<LeaveApplication> leaveApplications;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(LeaveApplication.class);
      leaveApplications =
          criteria
              .createAlias("subject", "subject")
              .add(
                  Restrictions.conjunction(
                      Restrictions.eq("terminated", true),
                      Restrictions.eq("approvedByHR", true),
                      Restrictions.le("startDate", dateRange.getEndDate()),
                      Restrictions.ge("endDate", dateRange.getStartDate())))
              .add(Restrictions.eq("subject.subjectId", subjectId))
              .setReadOnly(true)
              .list();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return leaveApplications;
  }
}
