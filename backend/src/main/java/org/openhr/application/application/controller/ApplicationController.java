package org.openhr.application.application.controller;

import java.io.IOException;
import org.openhr.application.application.facade.ApplicationFacade;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/applications")
public class ApplicationController {
  private final ApplicationFacade applicationFacade;

  public ApplicationController(final ApplicationFacade applicationFacade) {
    this.applicationFacade = applicationFacade;
  }

  @RequestMapping(
    value = "/{applicationId}/ics",
    method = RequestMethod.GET,
    produces = {"text/calendar"}
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<byte[]> getApplicationICSFile(@PathVariable final long applicationId)
      throws ApplicationDoesNotExistException, IOException {
    final HttpHeaders httpHeaders = new HttpHeaders();
    final byte[] calendarFile = applicationFacade.getApplicationICSFile(applicationId);
    httpHeaders.add("Content-Disposition", "attachment; filename=\"event.ics\"");
    return new ResponseEntity<>(calendarFile, httpHeaders, HttpStatus.OK);
  }
}
