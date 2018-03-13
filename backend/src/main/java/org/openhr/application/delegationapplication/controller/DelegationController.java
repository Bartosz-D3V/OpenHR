package org.openhr.application.delegationapplication.controller;

import org.openhr.application.delegationapplication.facade.DelegationFacade;
import org.openhr.common.domain.country.Country;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/delegations")
public class DelegationController {
  private final DelegationFacade delegationFacade;

  public DelegationController(final DelegationFacade delegationFacade) {
    this.delegationFacade = delegationFacade;
  }

  @RequestMapping(value = "/countries", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<Country> getCountries() {
    return delegationFacade.getCountries();
  }
}
