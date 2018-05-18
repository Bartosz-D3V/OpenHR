package org.openhr.application.adminconfiguration.controller;

import org.openhr.application.adminconfiguration.domain.AllowanceSettings;
import org.openhr.application.adminconfiguration.facade.AdminConfigurationFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin-configuration")
public class AdminConfigurationController {
  private final AdminConfigurationFacade adminConfigurationFacade;

  public AdminConfigurationController(final AdminConfigurationFacade adminConfigurationFacade) {
    this.adminConfigurationFacade = adminConfigurationFacade;
  }

  @RequestMapping(
    value = "/allowance-settings",
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public AllowanceSettings getAllowanceSettings() {
    return adminConfigurationFacade.getAllowanceSettings();
  }
}
