package com.tmm.enterprise.microblog.controller;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Team;
import com.tmm.enterprise.microblog.domain.enums.UserRole;
import com.tmm.enterprise.microblog.security.Account;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.JsonService;

import edu.emory.mathcs.backport.java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class TeamControllerTest {

	private static final String CONTACT_NAME_1 = "contact1";
	@Mock
	private AccountService mockAccountService;
	@Mock
	private ContactableService mockContactService;
	@Mock
	private JsonService mockJsonService;
	@Mock
	private Account mockAccount;
	@Mock
	private Team mockTeam;
	@Mock
	private Person mockPerson1, mockPerson2;

	private MockHttpServletRequest mockRequest;
	private MockHttpServletResponse mockResponse;
	private TeamController teamController;

	@Before
	public void setUp() throws Exception {
		teamController = new TeamController();
		teamController.setContactService(mockContactService);
		teamController.setAccountService(mockAccountService);
		teamController.setJsonService(mockJsonService);
		mockRequest = new MockHttpServletRequest();
		mockResponse = new MockHttpServletResponse();
	}

	@Test
	public void viewTeam() throws Exception {
		mockRequest.setRemoteUser(CONTACT_NAME_1);
		when(mockAccountService.loadAccountByUserName(CONTACT_NAME_1)).thenReturn(mockAccount);
		when(mockContactService.loadContactableByName("test team")).thenReturn(mockTeam);
		when(mockTeam.getMembers()).thenReturn(Arrays.asList(new Object[] { mockPerson1, mockPerson2 }));
		when(mockPerson1.getRole()).thenReturn(UserRole.MANAGER);
		when(mockPerson2.getRole()).thenReturn(UserRole.MEMBER);
		JsonObject p1 = new JsonObject();
		JsonObject p2 = new JsonObject();
		p1.addProperty("mockPerson", "manager person1");
		p2.addProperty("mockPerson", "member person2");
		when(mockJsonService.convertToJson(mockPerson1)).thenReturn(p1);
		when(mockJsonService.convertToJson(mockPerson2)).thenReturn(p2);

		ModelAndView actualMAV = teamController.viewTeam("test team", mockRequest, mockResponse);

		assertEquals("Returned model different.", "{managers=[{\"mockPerson\":\"manager person1\"}], teamname=null, "
				+ "teamdesc=null, members=[{\"mockPerson\":\"member person2\"}]}", actualMAV.getModel().toString());
		assertEquals("teamhomepage", actualMAV.getViewName());
	}
}
