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

import com.tmm.enterprise.microblog.domain.Notification;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Status;
import com.tmm.enterprise.microblog.domain.enums.UserRole;
import com.tmm.enterprise.microblog.security.Account;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ActivityService;
import com.tmm.enterprise.microblog.service.JsonService;
import com.tmm.enterprise.microblog.service.NotificationService;

import edu.emory.mathcs.backport.java.util.Arrays;

public class ActivityControllerTest {

	private ActivityController controller;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	private ActivityService activityService;
	private AccountService accountService;
	private NotificationService notificationService;
	private JsonService jsonService;

	private Person p;
	private Status s;
	private Date now;
	private Notification n;
	private Account acc;

	@Before
	public void setUp() {
		activityService = mock(ActivityService.class);
		accountService = mock(AccountService.class);
		notificationService = mock(NotificationService.class);
		jsonService = new JsonService();

		controller = new ActivityController();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();

		controller.setActivityService(activityService);
		controller.setAccountService(accountService);
		controller.setNotificationService(notificationService);
		controller.setJsonService(jsonService);

		p = new Person();
		p.setRole(UserRole.MEMBER);
		p.setId(1l);

		acc = new Account();
		acc.setUserProfile(p);

		s = new Status();
		s.setStatus("sent status message..");
		s.setRaisedBy(p);
		p.addStatus(s);
		s.setId(99l);
		now = new Date();
		s.setCreationDate(now);

		n = new Notification();
		n.setActivity(s);
		n.setId(100l);

	}

	@Test
	public void testLatestStatus() {
		List<Status> statuses = new ArrayList<Status>();
		statuses.add(s);
		when(notificationService.loadNotificationsForUser(p, 0)).thenReturn(
				Arrays.asList(new Notification[] { n }));
		when(accountService.getPerson(request)).thenReturn(p);

		try {
			ModelAndView mav = controller.latestActivity(0, 0, request);
			assertEquals("ajax_status", mav.getViewName());
			assertEquals(
					"{statuses={\"statuses\":[{\"body\":\"sent status message..\",\"title\":\"sent status message..\",\"createdAt\":\""
							+ now.toString()
							+ "\",\"createdBy\":\"No Account Found\",\"id\":99,\"displayDate\":\"just now\",\"objectType\":\"STATUS\",\"isOwner\":true}],\"latestId\":99}}",
					mav.getModel().toString());
		} catch (Exception e) {
			fail("uncaught exception");
		}

	}

	@Test
	public void testLatestUserActivity() {
		List<Status> statuses = new ArrayList<Status>();
		statuses.add(s);
		when(activityService.loadLatestPublicStatus(p, 0)).thenReturn(
				Arrays.asList(new Status[] { s }));
		String userName = "test";
		when(accountService.loadAccountByUserName(userName)).thenReturn(acc);

		try {
			ModelAndView mav = controller.latestUserActivity(userName, 0l, 0, request);
			assertEquals("ajax_status", mav.getViewName());
			assertEquals(
					"{statuses={\"statuses\":[{\"body\":\"sent status message..\",\"title\":\"sent status message..\",\"createdAt\":\""
							+ now.toString()
							+ "\",\"createdBy\":\"No Account Found\",\"id\":99,\"displayDate\":\"just now\",\"objectType\":\"STATUS\"}],\"latestId\":99}}",
					mav.getModel().toString());
		} catch (Exception e) {
			fail("uncaught exception");
		}
	}
}
