package org.openhr.application.delegationapplication.facade;

import org.openhr.application.delegationapplication.service.DelegationService;
import org.openhr.common.domain.country.Country;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DelegationFacadeImpl implements DelegationFacade {
  private final DelegationService delegationService;

  public DelegationFacadeImpl(final DelegationService delegationService) {
    this.delegationService = delegationService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Country> getCountries() {
    return delegationService.getCountries();
  }
}
