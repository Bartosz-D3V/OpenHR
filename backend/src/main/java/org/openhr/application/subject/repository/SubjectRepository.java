package org.openhr.application.subject.repository;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.ne;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SubjectRepository {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final SessionFactory sessionFactory;
  private final SubjectDAO subjectDAO;
  private final MessageSource messageSource;

  public SubjectRepository(
      final SessionFactory sessionFactory,
      final SubjectDAO subjectDAO,
      final MessageSource messageSource) {
    this.sessionFactory = sessionFactory;
    this.subjectDAO = subjectDAO;
    this.messageSource = messageSource;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<Subject> getSubjects() {
    List<Subject> subjects;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(Subject.class);
      subjects = (List<Subject>) criteria.list();
      session.flush();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    return subjects;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getSubjectDetails(final long subjectId) throws SubjectDoesNotExistException {
    return this.subjectDAO.getSubjectDetails(subjectId);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getSubjectDetailsByEmail(final String email, final Optional<String> excludeEmail)
      throws SubjectDoesNotExistException {
    Subject subject;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria =
          session
              .createCriteria(Subject.class)
              .createAlias("contactInformation", "contactInformation");
      subject =
          (Subject)
              criteria
                  .add(
                      Restrictions.conjunction(eq("contactInformation.email", email))
                          .add(ne("contactInformation.email", excludeEmail.orElse(null))))
                  .setMaxResults(1)
                  .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    if (subject == null) {
      throw new SubjectDoesNotExistException(
          messageSource.getMessage("error.subjectdoesnotexist", null, Locale.getDefault()));
    }

    return subject;
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void updateSubjectPersonalInformation(
      final long subjectId, final PersonalInformation personalInformation)
      throws SubjectDoesNotExistException {
    this.subjectDAO.updateSubjectPersonalInformation(subjectId, personalInformation);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void updateSubjectContactInformation(
      final long subjectId, final ContactInformation contactInformation)
      throws SubjectDoesNotExistException {
    this.subjectDAO.updateSubjectContactInformation(subjectId, contactInformation);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void updateSubjectEmployeeInformation(
      final long subjectId, final EmployeeInformation employeeInformation)
      throws SubjectDoesNotExistException {
    this.subjectDAO.updateSubjectEmployeeInformation(subjectId, employeeInformation);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void updateSubjectHRInformation(final long subjectId, final HrInformation hrInformation) {
    this.subjectDAO.updateSubjectHRInformation(subjectId, hrInformation);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LightweightSubjectDTO getLightweightSubject(final long subjectId)
      throws SubjectDoesNotExistException {
    LightweightSubjectDTO lightweightSubjectDTO;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(Subject.class);
      lightweightSubjectDTO =
          (LightweightSubjectDTO)
              criteria
                  .createAlias("personalInformation", "personalInfo")
                  .createAlias("employeeInformation", "employeeInfo")
                  .add(Restrictions.eq("subjectId", subjectId))
                  .setProjection(
                      Projections.distinct(
                          Projections.projectionList()
                              .add(Projections.property("subjectId"), "subjectId")
                              .add(Projections.property("personalInfo.firstName"), "firstName")
                              .add(Projections.property("personalInfo.lastName"), "lastName")
                              .add(Projections.property("employeeInfo.position"), "position")))
                  .setResultTransformer(Transformers.aliasToBean(LightweightSubjectDTO.class))
                  .uniqueResult();
      session.flush();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    if (lightweightSubjectDTO == null) {
      throw new SubjectDoesNotExistException(
          messageSource.getMessage("error.subjectdoesnotexist", null, Locale.getDefault()));
    }

    return lightweightSubjectDTO;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<LightweightSubjectDTO> getLightweightSubjects() {
    List<LightweightSubjectDTO> lightweightSubjectDTOS;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(Subject.class);
      lightweightSubjectDTOS =
          (List<LightweightSubjectDTO>)
              criteria
                  .createAlias("personalInformation", "personalInfo")
                  .createAlias("employeeInformation", "employeeInfo")
                  .setProjection(
                      Projections.distinct(
                          Projections.projectionList()
                              .add(Projections.property("subjectId"), "subjectId")
                              .add(Projections.property("personalInfo.firstName"), "firstName")
                              .add(Projections.property("personalInfo.lastName"), "lastName")
                              .add(Projections.property("employeeInfo.position"), "position")))
                  .setResultTransformer(Transformers.aliasToBean(LightweightSubjectDTO.class))
                  .setReadOnly(true)
                  .list();
      session.flush();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return lightweightSubjectDTOS;
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void updateEmail(final long subjectId, final String updatedEmail) {
    subjectDAO.updateEmail(subjectId, updatedEmail);
  }
}
