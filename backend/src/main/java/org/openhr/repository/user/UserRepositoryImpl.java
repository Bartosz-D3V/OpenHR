package org.openhr.repository.user;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openhr.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepositoryImpl implements UserRepository {
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public UserRepositoryImpl(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public User findByEmail(String email) {
    User user;
    try {
      final Session session = sessionFactory.openSession();
      user = session.get(User.class, email);
      session.close();
    } catch (HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return user;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void registerUser(final User user) {
    try {
      final Session session = sessionFactory.openSession();
      session.save(user);
      session.close();
    } catch (HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
  }

}