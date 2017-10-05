package org.openhr.myLeave;

import org.openhr.myLeave.facade.MyLeaveFacade;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyLeaveController {

  private final MyLeaveFacade myLeaveFacade;

  public MyLeaveController(final MyLeaveFacade myLeaveFacade) {
    this.myLeaveFacade = myLeaveFacade;
  }

}
