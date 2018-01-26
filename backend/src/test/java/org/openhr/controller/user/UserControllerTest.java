package org.openhr.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.domain.user.User;
import org.openhr.facade.user.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
  private final static User mockUser = new User("username", "password");
  private final static ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserFacade userFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @WithMockUser
  public void registerUserShouldAcceptUserObject() throws Exception {
    final String userAsJson = objectMapper.writeValueAsString(mockUser);
    final MvcResult result = mockMvc
      .perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(userAsJson))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void getUserByUsernameShouldReturnUserByUsername() throws Exception {
    final String userAsJson = objectMapper.writeValueAsString(mockUser);
    when(userFacade.findByUsername("username")).thenReturn(mockUser);

    final MvcResult result = mockMvc
      .perform(get("/users/{username}", "username"))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
    assertEquals(userAsJson, result.getResponse().getContentAsString());
  }
}
