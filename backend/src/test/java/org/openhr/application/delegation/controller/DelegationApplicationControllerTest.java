package org.openhr.application.delegation.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
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
import org.openhr.application.delegation.domain.DelegationApplication;
import org.openhr.application.delegation.facade.DelegationApplicationFacade;
import org.openhr.common.domain.country.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(DelegationApplicationController.class)
public class DelegationApplicationControllerTest {

  @Autowired private ObjectMapper objectMapper;

  @Autowired private MockMvc mockMvc;

  @MockBean private DelegationApplicationFacade delegationApplicationFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @WithMockUser
  public void getCountriesShouldReturnListOfCountriesWithNoArguments() throws Exception {
    final List<Country> countries = new ArrayList<>();
    final Country columbia = new Country();
    columbia.setCountryId(1L);
    columbia.setCountryName("Columbia");
    columbia.setFlagUrl(null);
    countries.add(columbia);
    final String countriesAsJSON = objectMapper.writeValueAsString(countries);

    when(delegationApplicationFacade.getCountries()).thenReturn(countries);

    final MvcResult result =
        mockMvc.perform(get("/delegations/countries")).andExpect(status().isOk()).andReturn();

    assertNull(result.getResolvedException());
    assertEquals(countriesAsJSON, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void startDelegationApplicationProcessShouldTakeSubjectIdAndApplicationObject()
      throws Exception {
    final DelegationApplication delegationApplication = new DelegationApplication();
    final String delegationApplicationAsJSON =
        objectMapper.writeValueAsString(delegationApplication);

    when(delegationApplicationFacade.startDelegationApplicationProcess(anyLong(), anyObject()))
        .thenReturn(delegationApplication);

    final MvcResult result =
        mockMvc
            .perform(
                post("/delegations")
                    .param("subjectId", String.valueOf(1L))
                    .content(delegationApplicationAsJSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(delegationApplicationAsJSON, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void
      getSubjectsDelegationApplicationsShouldAcceptSubjectIdAsParameterAndReturnListOfApplications()
          throws Exception {
    final List<DelegationApplication> applications = new ArrayList<>();
    final DelegationApplication delegationApplication = new DelegationApplication();
    applications.add(delegationApplication);
    final String delegationApplicationsAsJSON = objectMapper.writeValueAsString(applications);

    when(delegationApplicationFacade.getSubjectsDelegationApplications(1L))
        .thenReturn(applications);

    final MvcResult result =
        mockMvc
            .perform(get("/delegations").param("subjectId", String.valueOf(1L)))
            .andExpect(status().isOk())
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(delegationApplicationsAsJSON, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void getDelegationApplicationShouldReturnDelegationApplication() throws Exception {
    final DelegationApplication delegationApplication = new DelegationApplication();
    final String delegationApplicationAsJSON =
        objectMapper.writeValueAsString(delegationApplication);

    when(delegationApplicationFacade.getDelegationApplication(1L))
        .thenReturn(delegationApplication);

    final MvcResult result =
        mockMvc
            .perform(get("/delegations/{delegationApplicationId}", 1L))
            .andExpect(status().isOk())
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(delegationApplicationAsJSON, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void getAwaitingForActionDelegationApplicationsShouldAcceptSubjectIdAsParam()
      throws Exception {
    final List<DelegationApplication> applications = new ArrayList<>();
    final DelegationApplication delegationApplication = new DelegationApplication();
    applications.add(delegationApplication);
    final String delegationApplicationsAsJSON = objectMapper.writeValueAsString(applications);

    when(delegationApplicationFacade.getAwaitingForActionDelegationApplications(1L))
        .thenReturn(applications);

    final MvcResult result =
        mockMvc
            .perform(get("/delegations/awaiting").param("subjectId", String.valueOf(1L)))
            .andExpect(status().isOk())
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(delegationApplicationsAsJSON, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void updateDelegationApplicationShouldAcceptPathVariableAndBody() throws Exception {
    final DelegationApplication delegationApplication = new DelegationApplication();
    final String delegationApplicationAsJSON =
        objectMapper.writeValueAsString(delegationApplication);

    when(delegationApplicationFacade.updateDelegationApplication(anyString(), anyObject()))
        .thenReturn(delegationApplication);

    final MvcResult result =
        mockMvc
            .perform(
                put("/delegations")
                    .param("processInstanceId", "12XLK")
                    .content(delegationApplicationAsJSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(delegationApplicationAsJSON, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void rejectDelegationApplicationByManagerShouldAcceptProcessIdAsParameter()
      throws Exception {
    final MvcResult result =
        mockMvc
            .perform(put("/delegations/manager-reject").param("processInstanceId", "12XLK"))
            .andExpect(status().isNoContent())
            .andReturn();

    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void approveDelegationApplicationByManagerShouldAcceptProcessIdAsParameter()
      throws Exception {
    final MvcResult result =
        mockMvc
            .perform(put("/delegations/manager-approve").param("processInstanceId", "12XLK"))
            .andExpect(status().isNoContent())
            .andReturn();

    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void rejectDelegationApplicationByHrShouldAcceptProcessIdAsParameter() throws Exception {
    final MvcResult result =
        mockMvc
            .perform(put("/delegations/hr-reject").param("processInstanceId", "12XLK"))
            .andExpect(status().isNoContent())
            .andReturn();

    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void approveDelegationApplicationByHrShouldAcceptProcessIdAsParameter() throws Exception {
    final MvcResult result =
        mockMvc
            .perform(put("/delegations/hr-approve").param("processInstanceId", "12XLK"))
            .andExpect(status().isNoContent())
            .andReturn();

    assertNull(result.getResolvedException());
  }
}
