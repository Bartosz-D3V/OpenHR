package org.openhr.controller.myDetails;

import org.openhr.domain.subject.Subject;
import org.openhr.facade.myDetails.MyDetailsFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "my-details")
public class MyDetailsController {
  private final MyDetailsFacade MyDetailsFacade;

  public MyDetailsController(final MyDetailsFacade MyDetailsFacade) {
    this.MyDetailsFacade = MyDetailsFacade;
  }

  @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  public Subject getSubjectDetails(@RequestParam final long subjectId) {
    return this.MyDetailsFacade.getSubjectDetails(subjectId);
  }
}
