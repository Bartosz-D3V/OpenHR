package org.openhr.application.delegationapplication.facade;

import org.openhr.common.domain.country.Country;

import java.util.List;

public interface DelegationFacade {
  List<Country> getCountries();
}
