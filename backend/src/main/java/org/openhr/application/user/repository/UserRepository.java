package org.openhr.application.user.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.openhr.application.user.dao.UserDAO;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserDoesNotExist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

@Repository
@Transactional
public class UserRepository {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final SessionFactory sessionFactory;
  private final UserDAO userDAO;

  public UserRepository(final SessionFactory sessionFactory,
                        final UserDAO userDAO) {
    this.sessionFactory = sessionFactory;
    this.userDAO = userDAO;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public User getUser(final long userId) {
    return userDAO.getUser(userId);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public User getUserBySubjectId(final long subjectId) {
    User user;
    try {
      final Session session = sessionFactory.getCurrentSession();
      user = (User) session
        .createCriteria(Subject.class)
        .add(eq("subjectId", subjectId))
        .setProjection(Projections.property("user"))
        .setReadOnly(true)
        .setCacheable(true)
        .uniqueResult();
      session.flush();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    return user;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public User getUserByUsername(final String username) {
    User user;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(User.class);
      user = (User) criteria
        .add(eq("username", username))
        .setMaxResults(1)
        .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return user;
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public User updateUser(final long userId, final User user) {
    return userDAO.updateUser(userId, user);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long findUserId(final String username) {
    long userId;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(User.class);
      userId = (long) criteria
        .setProjection(Projections.property("userId"))
        .add(eq("username", username))
        .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return userId;
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void registerUser(final User user) {
    userDAO.registerUser(user);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public String getEncodedPassword(final long userId) throws UserDoesNotExist {
    String encodedPassword;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(User.class);
      encodedPassword = (String) criteria
        .setProjection(Projections.property("password"))
        .add(eq("userId", userId))
        .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    if (encodedPassword == null) {
      throw new UserDoesNotExist("User does not exists");
    }

    return encodedPassword;
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<String> retrieveUsernamesInUse() {
    List<String> usernamesInUse;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(User.class);
      usernamesInUse = criteria
        .setProjection(Projections.property("username"))
        .setReadOnly(true)
        .list();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return usernamesInUse;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long findSubjectId(final String username) throws SubjectDoesNotExistException {
    long subjectId;
    try {
      final Session session = sessionFactory.getCurrentSession();
      subjectId = (long) session.createCriteria(Subject.class)
        .setProjection(Projections.property("subjectId"))
        .createCriteria("user")
        .add(eq("username", username))
        .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    } catch (final NullPointerException e) {
      throw new SubjectDoesNotExistException("Subject does not exist");
    }
    if (subjectId == 0) {
      throw new SubjectDoesNotExistException("Subject does not exist");
    }

    return subjectId;
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void updateNotificationsSettings(final long userId, final boolean notificationsTurnedOn) {
    final User user = getUserBySubjectId(userId);
    user.setNotificationsTurnedOn(notificationsTurnedOn);
    userDAO.updateUser(userId, user);
  }
}
