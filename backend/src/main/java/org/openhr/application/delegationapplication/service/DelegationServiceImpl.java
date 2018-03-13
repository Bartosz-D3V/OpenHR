package org.openhr.application.delegationapplication.service;

import org.openhr.application.delegationapplication.repository.DelegationRepository;
import org.openhr.common.domain.country.Country;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DelegationServiceImpl implements DelegationService {
  private final DelegationRepository delegationRepository;

  public DelegationServiceImpl(final DelegationRepository delegationRepository) {
    this.delegationRepository = delegationRepository;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Country> getCountries() {
    return delegationRepository.getCountries();
  }
}
