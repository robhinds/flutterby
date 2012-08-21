package com.tmm.enterprise.microblog.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Team;
import com.tmm.enterprise.microblog.domain.enums.UserRole;
import com.tmm.enterprise.microblog.security.Account;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.JsonService;

public class PeopleControllerTest {

	private PeopleController controller;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private ContactableService contactService;
	private JsonService jsonService;

	@Before
	public void setUp() {
		contactService = mock(ContactableService.class);
		jsonService = new JsonService();

		controller = new PeopleController();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();

		controller.setContactService(contactService);
		controller.setJsonService(jsonService);
	}

	@Test
	public void testLoadDirectory() {
		Person p = new Person();
		p.setRole(UserRole.MEMBER);
		p.setId(99l);
		Account acc = new Account();
		acc.setUserName("rob");
		acc.setId(999l);
		acc.setUserProfile(p);
		p.setLinkedAccount(acc);

		Team t = new Team();
		t.setName("dev team");
		t.setDescription("na");
		t.addMember(p);
		p.setTeam(t);
		List<Team> ts = new ArrayList<Team>();
		ts.add(t);

		when(contactService.loadAllTeams()).thenReturn(ts);

		try {
			ModelAndView mav = controller.loadDirectory(request, response);
			assertEquals("ajax_people", mav.getViewName());
			assertEquals(
					"{directory=[{\"id\":null,\"name\":\"dev team\",\"description\":\"na\",\"objectType\":\"TEAM\","
							+ "\"members\":[{\"id\":99,\"name\":\"rob\",\"role\":\"MEMBER\",\"teamName\":\"dev team\","
							+ "\"objectType\":\"PERSON\",\"status\":\"No Status Set Yet\"}]},{\"id\":null,\"name\":\"No Team\","
							+ "\"description\":\"No Description Provided\",\"objectType\":\"TEAM\",\"members\":[]}]}",
					mav.getModel().toString());
		} catch (Exception e) {
			fail("unexpected exception");
		}
	}

}
