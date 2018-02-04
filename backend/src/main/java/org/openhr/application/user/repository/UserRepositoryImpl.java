package org.openhr.application.user.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.domain.UserRole;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.enumeration.UserPermissionRole;
import org.openhr.common.exception.UserDoesNotExist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public UserRepositoryImpl(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public User findByUsername(final String username) {
    User user;
    try {
      final Session session = sessionFactory.openSession();
      final Criteria criteria = session.createCriteria(User.class);
      user = (User) criteria
        .add(Restrictions.eq("username", username))
        .setMaxResults(1)
        .uniqueResult();
      session.close();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return user;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long findUserId(final String username) {
    long userId;
    try {
      final Session session = sessionFactory.openSession();
      final Criteria criteria = session.createCriteria(User.class);
      userId = (long) criteria
        .setProjection(Projections.property("userId"))
        .add(Restrictions.eq("username", username))
        .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return userId;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void registerUser(final User user) {
    final List<UserRole> permissions = new ArrayList<>();
    final UserRole userRole = new UserRole(UserPermissionRole.MEMBER);
    permissions.add(userRole);
    user.setUserRoles(permissions);
    userRole.setUser(user);
    try {
      final Session session = sessionFactory.openSession();
      session.save(userRole);
      session.save(user);
      session.close();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public String getEncodedPassword(final long userId) {
    String encodedPassword;
    try {
      final Session session = sessionFactory.openSession();
      final Criteria criteria = session.createCriteria(User.class);
      encodedPassword = (String) criteria
        .setProjection(Projections.property("password"))
        .add(Restrictions.eq("userId", userId))
        .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return encodedPassword;
  }

  @Override
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<String> retrieveUsernamesInUse() {
    List<String> usernamesInUse;
    try {
      final Session session = sessionFactory.openSession();
      final Criteria criteria = session.createCriteria(User.class);
      usernamesInUse = criteria
        .setProjection(Projections.property("username"))
        .setReadOnly(true)
        .list();
      session.close();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return usernamesInUse;
  }

  @Override
  public long findSubjectId(final String username) throws UserDoesNotExist {
    long subjectId;
    try {
      final Session session = sessionFactory.openSession();
      subjectId = (long) session.createCriteria(Subject.class)
        .add(Restrictions.eq("user.username", username))
        .setProjection(Projections.property("subjectId"))
        .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    if (subjectId == 0) {
      throw new UserDoesNotExist("User does not exist");
    }

    return subjectId;
  }
}
