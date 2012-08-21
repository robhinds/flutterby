package com.tmm.enterprise.microblog.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.security.Account;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.JsonService;

public class HomeControllerTest {

	private HomeController controller;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	private AccountService accountService;
	private ContactableService contactService;
	private JsonService jsonService;

	@Before
	public void setUp() {
		accountService = mock(AccountService.class);
		contactService = mock(ContactableService.class);
		jsonService = new JsonService();

		controller = new HomeController();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();

		controller.setAccountService(accountService);
		controller.setContactService(contactService);
		controller.setJsonService(jsonService);
	}

	@Test
	public void testAnonhome() {
		Account acc = new Account();
		acc.setId(1l);
		acc.setUserName("rob");

		when(accountService.loadAccountByUserName("rob")).thenReturn(acc);
		when(contactService.loadAllContactables()).thenReturn(
				new ArrayList<Contactable>());

		request.setRemoteUser("rob");

		ModelAndView mav;
		try {
			mav = controller.anonhome(request);
			assertEquals("userhomepage", mav.getViewName());
			assertEquals(
					"{contactsPersons=[], username=rob, contactsTeams=[], teamname=N/A, currentStatus=<i>You do not currently have any status set</i>}",
					mav.getModel().toString());
		} catch (Exception e) {
			fail("unexpected exception");
		}
	}

}
