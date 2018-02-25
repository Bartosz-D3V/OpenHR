package org.openhr.application.subject.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.openhr.application.subject.dto.LightweightSubjectDTO;
import org.openhr.common.dao.BaseDAO;
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
public class SubjectDAOImpl extends BaseDAO implements SubjectDAO {

  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public SubjectDAOImpl(final SessionFactory sessionFactory) {
    super(sessionFactory);
    this.sessionFactory = sessionFactory;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getSubjectDetails(final long subjectId) throws SubjectDoesNotExistException, HibernateException {
    final Subject subject = (Subject) super.get(Subject.class, subjectId);
    if (subject == null) {
      log.error("Subject could not be found");
      throw new SubjectDoesNotExistException("Subject could not be found");
    }

    return subject;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  protected Subject getExistingSubjectDetails(final long subjectId) throws HibernateException {
    return (Subject) super.get(Subject.class, subjectId);
  }

  @Override
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

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = HibernateException.class)
  public void updateSubjectPersonalInformation(final long subjectId, final PersonalInformation personalInformation)
    throws HibernateException, SubjectDoesNotExistException {
    final Subject subject = getSubjectDetails(subjectId);
    subject.setPersonalInformation(personalInformation);
    super.merge(subject);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = HibernateException.class)
  public void updateSubjectContactInformation(final long subjectId, final ContactInformation contactInformation)
    throws HibernateException, SubjectDoesNotExistException {
    final Subject subject = getSubjectDetails(subjectId);
    subject.setContactInformation(contactInformation);
    super.merge(subject);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = HibernateException.class)
  public void updateSubjectEmployeeInformation(final long subjectId, final EmployeeInformation employeeInformation)
    throws HibernateException, SubjectDoesNotExistException {
    final Subject subject = getSubjectDetails(subjectId);
    subject.setEmployeeInformation(employeeInformation);
    super.merge(subject);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = HibernateException.class)
  public void updateSubjectHRInformation(final long subjectId, final HrInformation hrInformation)
    throws HibernateException {
    final Subject subject = getExistingSubjectDetails(subjectId);
    subject.setHrInformation(hrInformation);
    super.merge(subject);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = HibernateException.class)
  public void deleteSubject(final long subjectId) throws HibernateException, SubjectDoesNotExistException {
    try {
      final Session session = sessionFactory.getCurrentSession();
      session.delete(getSubjectDetails(subjectId));
      session.flush();
    } catch (final HibernateException hibernateException) {
      log.error("Issue occurred during the deletion of the subject");
      log.error(hibernateException.getMessage());
      throw hibernateException;
    } catch (final SubjectDoesNotExistException subjectDoesNotExistException) {
      log.error(subjectDoesNotExistException.getMessage());
      throw subjectDoesNotExistException;
    }
  }

  @Override
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

  @Override
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
