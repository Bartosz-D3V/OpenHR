package org.openhr.application.subject.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.openhr.application.subject.dao.SubjectDAO;
import org.openhr.application.subject.dto.LightweightSubjectDTO;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SubjectRepository {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final SessionFactory sessionFactory;
  private final SubjectDAO subjectDAO;

  public SubjectRepository(final SessionFactory sessionFactory,
                           final SubjectDAO subjectDAO) {
    this.sessionFactory = sessionFactory;
    this.subjectDAO = subjectDAO;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getSubjectDetails(final long subjectId) throws SubjectDoesNotExistException {
    return this.subjectDAO.getSubjectDetails(subjectId);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void updateSubjectPersonalInformation(final long subjectId, final PersonalInformation personalInformation)
    throws SubjectDoesNotExistException {
    this.subjectDAO.updateSubjectPersonalInformation(subjectId, personalInformation);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void updateSubjectContactInformation(final long subjectId, final ContactInformation contactInformation)
    throws SubjectDoesNotExistException {
    this.subjectDAO.updateSubjectContactInformation(subjectId, contactInformation);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void updateSubjectEmployeeInformation(final long subjectId, final EmployeeInformation employeeInformation)
    throws SubjectDoesNotExistException {
    this.subjectDAO.updateSubjectEmployeeInformation(subjectId, employeeInformation);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void updateSubjectHRInformation(final long subjectId, final HrInformation hrInformation) {
    this.subjectDAO.updateSubjectHRInformation(subjectId, hrInformation);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteSubject(final long subjectId) throws SubjectDoesNotExistException {
    this.subjectDAO.deleteSubject(subjectId);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LightweightSubjectDTO getLightweightSubject(final long subjectId) throws SubjectDoesNotExistException {
    LightweightSubjectDTO lightweightSubjectDTO;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(Subject.class);
      lightweightSubjectDTO = (LightweightSubjectDTO) criteria
        .createAlias("personalInformation", "personalInfo")
        .add(Restrictions.eq("subjectId", subjectId))
        .setProjection(Projections.distinct(Projections.projectionList()
          .add(Projections.property("subjectId"), "subjectId")
          .add(Projections.property("personalInfo.firstName"), "firstName")
          .add(Projections.property("personalInfo.lastName"), "lastName")))
        .setResultTransformer(Transformers.aliasToBean(LightweightSubjectDTO.class))
        .uniqueResult();
      session.flush();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    if (lightweightSubjectDTO == null) {
      throw new SubjectDoesNotExistException("Subject could not be found");
    }

    return lightweightSubjectDTO;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long getAllowance(final long subjectId) {
    long allowance;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(Subject.class);
      allowance = (long) criteria
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
      usedAllowance = (long) criteria
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
}