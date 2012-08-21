package com.tmm.enterprise.microblog.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.PrivateMessage;
import com.tmm.enterprise.microblog.domain.enums.UserRole;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.JsonService;
import com.tmm.enterprise.microblog.service.MessageService;

public class EmailControllerTest {

	private EmailController controller;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	private MessageService emailService;
	private ContactableService contactService;
	private AccountService accountService;
	private JsonService jsonService;

	@Before
	public void setUp() throws Exception {

		emailService = mock(MessageService.class);
		contactService = mock(ContactableService.class);
		accountService = mock(AccountService.class);
		jsonService = new JsonService();

		controller = new EmailController();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();

		controller.setAccountService(accountService);
		controller.setContactService(contactService);
		controller.setEmailService(emailService);
		controller.setJsonService(jsonService);
	}

	@Test
	public void testHandleNoSuchRequestHandlingMethod() {
		try {
			ModelAndView mav = controller.handleNoSuchRequestHandlingMethod(
					null, request, response);
			assertEquals("RedirectView", mav.getView().getClass()
					.getSimpleName());
		} catch (Exception e) {
			fail("Unexpected Exception");
		}
	}

	@Test
	public void inboxTest() {
		Person p = new Person();
		p.setRole(UserRole.MEMBER);
		Date now = new Date();

		PrivateMessage pm = new PrivateMessage();
		pm.setDetails("example email");
		pm.setTitle("topic");
		pm.setRaisedBy(p);
		pm.setAssignedTo(p);
		pm.setId(1l);
		pm.setCreationDate(now);

		p.addReceivedMessage(pm);
		p.addSentMessage(pm);

		when(accountService.getPerson(request)).thenReturn(p);

		// execute test
		try {
			// ModelAndView mav = controller.inbox(request, response);
			// assertEquals("emailFolder", mav.getViewName());
			// assertEquals("{model={emails=[{\"body\":\"example email\",\"createdBy\":\"No Account Found\",\"createdAt\":\""
			// + now.toString()
			// +
			// "\",\"displayDate\":\"just now\",\"id\":1,\"title\":\"topic\",\"recipients\":[{\"id\":null,"
			// +
			// "\"name\":\"No Account Found\",\"role\":\"MEMBER\",\"teamName\":\"\"}]}]}}",
			// mav.getModel().toString());
		} catch (Exception e) {
			fail("unexpected exception");
		}

	}

	@Test
	public void testCompose() {
		Person p = new Person();
		p.setRole(UserRole.MEMBER);
		Date now = new Date();
		List<Person> ps = new ArrayList<Person>();
		ps.add(p);

		when(contactService.loadAllPersons()).thenReturn(ps);

		// execute test
		try {
			ModelAndView mav = controller.compose(request, response);
			assertEquals("emailCompose", mav.getViewName());
			assertEquals(
					"{model={contacts=[{\"id\":null,\"name\":\"No Account Found\",\"role\":\"MEMBER\",\"teamName\":\"\",\"objectType\":\"PERSON\",\"status\":\"No Status Set Yet\"}]}}",
					mav.getModel().toString());
		} catch (Exception e) {
			fail("unexpected exception");
		}
	}

	@Test
	public void testSendEmail() {
		// execute test
		request.addParameter("msg", "example message body text");
		request.addParameter("recipient", "999");
		try {
			ModelAndView mav = controller.sendEmail(request, response);
			assertEquals("ajax_email", mav.getViewName());
		} catch (Exception e) {
			fail("unexpected exception");
		}
	}

}
