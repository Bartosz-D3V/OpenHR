package org.openhr.application.adminconfiguration.dao;

import org.hibernate.SessionFactory;
import org.openhr.application.adminconfiguration.domain.AllowanceSettings;
import org.openhr.common.dao.BaseDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AdminConfigurationDAOImpl extends BaseDAO implements AdminConfigurationDAO {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  protected AdminConfigurationDAOImpl(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public AllowanceSettings getAllowanceSettings() {
    return (AllowanceSettings) super.get(AllowanceSettings.class, 1L);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public AllowanceSettings updateAllowanceSettings(final AllowanceSettings allowanceSettings) {
    super.merge(allowanceSettings);
    return allowanceSettings;
  }
}
