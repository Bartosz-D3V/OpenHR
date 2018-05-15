package org.openhr.application.application;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.application.controller.ApplicationController;
import org.openhr.application.application.facade.ApplicationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(ApplicationController.class)
public class ApplicationControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ApplicationFacade applicationFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @WithMockUser
  public void getApplicationICSFileShouldAcceptApplicationIdAsPathParam() throws Exception {
    final byte[] mockIcs = new byte[15];

    when(applicationFacade.getApplicationICSFile(1L)).thenReturn(mockIcs);

    final MvcResult result =
        mockMvc
            .perform(get("/applications/{applicationId}/ics", 1L).accept("text/calendar"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/calendar"))
            .andExpect(header().string("Content-Disposition", "attachment; filename=\"event.ics\""))
            .andReturn();

    assertNull(result.getResolvedException());
  }
}
