package org.openhr.application.user.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.dto.PasswordDTO;
import org.openhr.application.user.facade.UserFacade;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.common.exception.UserDoesNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final UserAlreadyExists userAlreadyExistsError =
      new UserAlreadyExists("User already exists");
  private static final User mockUser = new User("username", "password");

  @Autowired private MockMvc mockMvc;

  @MockBean private UserFacade userFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @WithMockUser
  public void getUserShouldReturnUser() throws Exception {
    when(userFacade.getUser(1L)).thenReturn(mockUser);

    final String userAsJson = objectMapper.writeValueAsString(mockUser);
    final MvcResult result =
        mockMvc.perform(get("/users/{userId}", 1L)).andExpect(status().isOk()).andReturn();

    assertEquals(result.getResponse().getContentAsString(), userAsJson);
  }

  @Test
  @WithMockUser
  public void registerUserShouldAcceptUserObject() throws Exception {
    final String userAsJson = objectMapper.writeValueAsString(mockUser);
    final MvcResult result =
        mockMvc
            .perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userAsJson))
            .andExpect(status().isAccepted())
            .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void registerUserShouldHandleUserAlreadyExistsError() throws Exception {
    doThrow(userAlreadyExistsError).when(userFacade).registerUser(anyObject());
    final String userAsJson = objectMapper.writeValueAsString(mockUser);
    final MvcResult result =
        mockMvc
            .perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userAsJson))
            .andExpect(status().isNotAcceptable())
            .andReturn();

    assertEquals(
        userAlreadyExistsError.getLocalizedMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser
  public void updateUserShouldAcceptUserAndUserId() throws Exception {
    when(userFacade.updateUser(anyLong(), anyObject())).thenReturn(mockUser);

    final String userAsJson = objectMapper.writeValueAsString(mockUser);
    final MvcResult result =
        mockMvc
            .perform(
                put("/users/{userId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(userAsJson))
            .andExpect(status().isOk())
            .andReturn();

    assertEquals(userAsJson, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void getUserBySubjectIdShouldReturnUser() throws Exception {
    when(userFacade.getUserBySubjectId(1L)).thenReturn(mockUser);

    final String userAsJson = objectMapper.writeValueAsString(mockUser);
    final MvcResult result =
        mockMvc
            .perform(get("/users").param("subjectId", String.valueOf(1L)))
            .andExpect(status().isOk())
            .andReturn();

    assertEquals(userAsJson, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void updateNotificationsSettingsShouldAcceptUserIdAndBooleanParam() throws Exception {
    final MvcResult result =
        mockMvc
            .perform(
                put("/users/{userId}/notifications", 1L)
                    .param("notificationsTurnedOn", String.valueOf(true)))
            .andExpect(status().isNoContent())
            .andReturn();

    assertNull(result.getResponse().getErrorMessage());
  }

  @Test
  @WithMockUser
  public void updatePasswordShouldAcceptPasswordDTO() throws Exception {
    final PasswordDTO passwordDTO = new PasswordDTO();
    passwordDTO.setOldPassword("Old password");
    passwordDTO.setNewPassword("New password");
    final String passwordAsJSON = objectMapper.writeValueAsString(passwordDTO);
    final MvcResult result =
        mockMvc
            .perform(
                put("/users/auth/{userId}", 1L)
                    .content(passwordAsJSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andReturn();

    assertNull(result.getResponse().getErrorMessage());
  }

  @Test
  @WithMockUser
  public void getUserByUsernameShouldReturnTrueInHeaderIfUsernameIsTaken() throws Exception {
    final MvcResult result =
        mockMvc
            .perform(head("/users").param("username", "mockUsername"))
            .andExpect(status().isNoContent())
            .andReturn();

    assertEquals(Boolean.toString(true), result.getResponse().getHeader("usernameTaken"));
  }

  @Test
  @WithMockUser
  public void getUserByUsernameShouldReturnFalseInHeaderIfUsernameIsAvailable() throws Exception {
    when(userFacade.getUserByUsername("mockUsername"))
        .thenThrow(new UserDoesNotExist("User does not exist"));

    final MvcResult result =
        mockMvc
            .perform(head("/users").param("username", "mockUsername"))
            .andExpect(status().isNoContent())
            .andReturn();

    assertEquals(Boolean.toString(false), result.getResponse().getHeader("usernameTaken"));
  }
}
