package org.openhr.controller.personaletails;

import org.openhr.domain.subject.Subject;
import org.openhr.facade.personaldetails.PersonalDetailsFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "my-details")
public class PersonalDetailsController {
  private final PersonalDetailsFacade personalDetailsFacade;

  public PersonalDetailsController(final PersonalDetailsFacade personalDetailsFacade) {
    this.personalDetailsFacade = personalDetailsFacade;
  }

  @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  public Subject getSubjectDetails(@RequestParam final long subjectId) {
    return this.personalDetailsFacade.getSubjectDetails(subjectId);
  }
}
