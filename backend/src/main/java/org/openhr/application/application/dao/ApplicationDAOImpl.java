package org.openhr.application.application.dao;

import org.hibernate.SessionFactory;
import org.openhr.common.dao.BaseDAO;
import org.openhr.common.domain.application.Application;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ApplicationDAOImpl extends BaseDAO implements ApplicationDAO {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public ApplicationDAOImpl(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Application getApplication(final long applicationId)
      throws ApplicationDoesNotExistException {
    final Application application = (Application) super.get(Application.class, applicationId);

    if (application == null) {
      log.error("Application could not be found");
      throw new ApplicationDoesNotExistException("Application could not be found");
    }
    return application;
  }
}
