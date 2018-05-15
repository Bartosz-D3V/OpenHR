package org.openhr.application.hr.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.hr.facade.HrFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(HrController.class)
public class HrControllerTest {
  private static final HrTeamMember hr = new HrTeamMember();

  @Autowired private ObjectMapper objectMapper;

  @Autowired private MockMvc mockMvc;

  @MockBean private HrFacade hrFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @WithMockUser
  public void getHrTeamMemberShouldReturnHrTeamMemberByPathVariable() throws Exception {
    when(hrFacade.getHrTeamMember(1L)).thenReturn(hr);

    final String hrAsJson = objectMapper.writeValueAsString(hr);

    final MvcResult result =
        mockMvc
            .perform(get("/hr-team-members/{subjectId}", 1L))
            .andExpect(status().isOk())
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(hrAsJson, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void getHrTeamMembersShouldReturnHrTeamMembers() throws Exception {
    final List<HrTeamMember> hrTeamMembers = new ArrayList<>();
    hrTeamMembers.add(hr);

    when(hrFacade.getHrTeamMembers()).thenReturn(hrTeamMembers);

    final String hrTeamMembersAsJson = objectMapper.writeValueAsString(hrTeamMembers);

    final MvcResult result =
        mockMvc.perform(get("/hr-team-members")).andExpect(status().isOk()).andReturn();

    assertNull(result.getResolvedException());
    assertEquals(hrTeamMembersAsJson, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void addHrShouldAcceptHrObjectAndReturnIt() throws Exception {
    when(hrFacade.addHrTeamMember(anyObject())).thenReturn(hr);

    final String hrAsJson = objectMapper.writeValueAsString(hr);

    final MvcResult result =
        mockMvc
            .perform(
                post("/hr-team-members").contentType(MediaType.APPLICATION_JSON).content(hrAsJson))
            .andExpect(status().isCreated())
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(hrAsJson, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void updateHrShouldAcceptHrObjectAndReturnIt() throws Exception {
    when(hrFacade.updateHrTeamMember(anyLong(), anyObject())).thenReturn(hr);

    final String hrAsJson = objectMapper.writeValueAsString(hr);

    final MvcResult result =
        mockMvc
            .perform(
                put("/hr-team-members/{subjectId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(hrAsJson))
            .andExpect(status().isOk())
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(hrAsJson, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void deleteHrTeamMemberShouldAcceptPathVariable() throws Exception {
    final MvcResult result =
        mockMvc
            .perform(delete("/hr-team-members/{subjectId}", 1L))
            .andExpect(status().isNoContent())
            .andReturn();

    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void addManagerToHrShouldAcceptTwoPathVariables() throws Exception {
    final MvcResult result =
        mockMvc
            .perform(
                post("/hr-team-members/manager-assignment")
                    .param("hrTeamMemberId", String.valueOf(1L))
                    .param("managerId", String.valueOf(2L)))
            .andExpect(status().isOk())
            .andReturn();

    assertNull(result.getResolvedException());
  }
}
