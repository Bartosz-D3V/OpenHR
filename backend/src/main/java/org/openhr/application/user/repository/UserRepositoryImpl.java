package org.openhr.application.user.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.domain.UserRole;
import org.openhr.common.dao.BaseDAO;
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

import static org.hibernate.criterion.Restrictions.eq;

@Repository
public class UserRepositoryImpl extends BaseDAO implements UserRepository {
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public UserRepositoryImpl(final SessionFactory sessionFactory) {
    super(sessionFactory);
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
        .add(eq("username", username))
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
        .add(eq("username", username))
        .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return userId;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void registerUser(final User user) {
    final List<UserRole> permissions = new ArrayList<>();
    final UserRole userRole = new UserRole(UserPermissionRole.MEMBER);
    permissions.add(userRole);
    user.setUserRoles(permissions);
    userRole.setUser(user);
    super.save(user);
    super.save(userRole);
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
        .add(eq("userId", userId))
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
        .setProjection(Projections.property("subjectId"))
        .createCriteria("user")
        .add(eq("username", username))
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
