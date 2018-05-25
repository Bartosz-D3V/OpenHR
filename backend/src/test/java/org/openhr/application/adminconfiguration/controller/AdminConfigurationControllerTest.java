package org.openhr.application.adminconfiguration.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.adminconfiguration.domain.AllowanceSettings;
import org.openhr.application.adminconfiguration.facade.AdminConfigurationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminConfigurationController.class)
public class AdminConfigurationControllerTest {
  private AllowanceSettings allowanceSettings;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private AdminConfigurationFacade adminConfigurationFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    allowanceSettings = new AllowanceSettings();
    allowanceSettings.setAutoResetAllowance(true);
    allowanceSettings.setCarryOverUnusedLeave(true);
    allowanceSettings.setNumberOfDaysToCarryOver(10);
    allowanceSettings.setResetDate(LocalDate.now());
  }

  @Test
  @WithMockUser
  public void getAllowanceSettingsShouldReturnAllowanceSettingsObject() throws Exception {
    final String allowanceSettingsAsJSON = objectMapper.writeValueAsString(allowanceSettings);

    when(adminConfigurationFacade.getAllowanceSettings()).thenReturn(allowanceSettings);

    final MvcResult result =
        mockMvc
            .perform(get("/admin-configuration/allowance-settings"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(allowanceSettingsAsJSON, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void updateAllowanceSettingsShouldReturnAllowanceSettingsObject() throws Exception {
    final String allowanceSettingsAsJSON = objectMapper.writeValueAsString(allowanceSettings);

    when(adminConfigurationFacade.updateAllowanceSettings(anyObject()))
        .thenReturn(allowanceSettings);

    final MvcResult result =
        mockMvc
            .perform(
                put("/admin-configuration/allowance-settings")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(allowanceSettingsAsJSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(allowanceSettingsAsJSON, result.getResponse().getContentAsString());
  }
}
