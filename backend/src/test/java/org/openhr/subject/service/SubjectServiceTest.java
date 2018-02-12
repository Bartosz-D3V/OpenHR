package org.openhr.subject.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.subject.dao.SubjectDAO;
import org.openhr.application.subject.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SubjectServiceTest {

  @Autowired
  private SubjectService subjectService;

  @MockBean
  private SubjectDAO subjectDAO;

  @Test
  public void getLeftAllowanceInDaysShouldReturnDiffBetweenAllowedLeaveAndUsedLeave() {
    when(subjectDAO.getAllowance(100L)).thenReturn(25L);
    when(subjectDAO.getUsedAllowance(100L)).thenReturn(10L);

    assertEquals(15L, subjectService.getLeftAllowanceInDays(100L));
  }
}
