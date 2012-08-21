package com.tmm.enterprise.microblog.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Status;
import com.tmm.enterprise.microblog.domain.enums.UserRole;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.StatusService;

public class StatusControllerTest {
	
	
	private StatusController controller;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	
	private StatusService statusService;
	private ContactableService contactService;
	private AccountService accountService;
	
	private Person p;
	private Status s;
	private Date now;
	
	@Before
	public void setUp(){
		statusService = mock(StatusService.class);
		contactService = mock(ContactableService.class);
		accountService = mock(AccountService.class);
		
		controller = new StatusController();
		request = new MockHttpServletRequest();
		response  = new MockHttpServletResponse();
		
		controller.setStatusService(statusService);
		
		p = new Person();
		p.setRole(UserRole.MEMBER);
		p.setId(1l);
		
		s = new Status();
		s.setStatus("sent status message..");
		s.setRaisedBy(p);
		p.addStatus(s);
		s.setId(99l);
		now = new Date();
		s.setCreationDate(now);
		
	}
	

	@Test
	public void testUpdateStatus() {
		when(accountService.getPerson(request)).thenReturn(p);
		
		request.addParameter("status", "example status text!");
		
		try {
			ModelAndView mav = controller.updateStatus(request, response);
			assertEquals("ajax_status", mav.getViewName());
			assertEquals("{status=example status text!}", mav.getModel().toString());
		} catch (Exception e) {
			fail("uncaught exception");
		}
	}

	@Test
	public void testRepeatStatus() {
		when(accountService.getPerson(request)).thenReturn(p);
		when(statusService.loadStatus(1l)).thenReturn(s);
		
		request.addParameter("status", "1");
		
		try {
			ModelAndView mav = controller.repeatStatus(request, response);
			assertEquals("ajax_status", mav.getViewName());
			assertEquals("{status=sent status message..}", mav.getModel().toString());
		} catch (Exception e) {
			fail("uncaught exception");
		}
	}
}