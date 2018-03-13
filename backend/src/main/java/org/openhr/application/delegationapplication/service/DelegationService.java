package org.openhr.application.delegationapplication.service;

import org.openhr.common.domain.country.Country;

import java.util.List;

public interface DelegationService {
  List<Country> getCountries();
}
