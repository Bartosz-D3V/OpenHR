package org.openhr.application.authentication.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.authentication.facade.AuthenticationFacade;
import org.openhr.security.domain.JWTAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private AuthenticationFacade authenticationFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @WithMockUser
  public void refreshTokenShouldReturnTokenInAHeader() throws Exception {
    when(authenticationFacade.parseToken(anyObject())).thenReturn("mock-payload");
    when(authenticationFacade.createToken("mock-payload"))
        .thenReturn(new JWTAccessToken("mock-token", null));

    final MvcResult result =
        mockMvc
            .perform(post("/auth/token").header("Authorization", "mock-token"))
            .andExpect(status().isNoContent())
            .andReturn();

    assertEquals("mock-token", result.getResponse().getHeader("Authorization"));
  }
}
