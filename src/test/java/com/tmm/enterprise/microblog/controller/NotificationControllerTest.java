package com.tmm.enterprise.microblog.controller;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.domain.Activity;
import com.tmm.enterprise.microblog.domain.Notification;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.enums.ObjectType;
import com.tmm.enterprise.microblog.renderer.NotificationRenderer;
import com.tmm.enterprise.microblog.renderer.NotificationRendererFactory;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.NotificationService;


@RunWith(MockitoJUnitRunner.class)
public class NotificationControllerTest {

	private static final String QUESTION = "QUESTION";
	private static final String CONTACT_NAME_1 = "contact1";
	@Mock 
	private NotificationService mockNotificationService; 
	@Mock 
	private ContactableService mockContactableService; 
	@Mock
	private NotificationRendererFactory mockNotificationRendererFactory; 
	@Mock
	private Person mockPerson; 
	@Mock
	private Notification mockNotification1,mockNotification2;
	@Mock
	private NotificationRenderer questionRenderer,answerRenderer,genericMockRenderer; 
	@Mock
	private Activity mockActivity; 
	
	private MockHttpServletRequest mockRequest; 
	private MockHttpServletResponse mockResponse ;
	private NotificationController notificationController ;
	@Before
	public void setUp() throws Exception {
		notificationController = new NotificationController(); 
		notificationController.setNotificationService(mockNotificationService);
		notificationController.setContactableService(mockContactableService);
		notificationController.setRendererFactory(mockNotificationRendererFactory);
		mockRequest = new MockHttpServletRequest(); 
		mockResponse = new MockHttpServletResponse();
	}


	@Test
	public void getQuestionNotifications() throws Exception {
		mockRequest.setRemoteUser(CONTACT_NAME_1);
		JsonObject questionJson = new JsonObject(); 
		questionJson.addProperty("activityId", 1);
		JsonObject answerJson = new JsonObject(); 
		answerJson.addProperty("activityId", 2);
		List<Notification> notifications = Arrays.asList(mockNotification1,mockNotification2); 
		when(mockNotification1.getDiscriminator()).thenReturn(QUESTION);
		when(mockContactableService.loadContactableByName(CONTACT_NAME_1)).thenReturn(mockPerson);
		when(mockNotificationService.loadUnreadNotificationsForUser(mockPerson,ObjectType.QUESTION,ObjectType.ANSWER)).thenReturn(notifications);
		when(mockNotificationRendererFactory.getNotificationRenderer(mockNotification1)).thenReturn(questionRenderer);
		when(mockNotificationRendererFactory.getNotificationRenderer(mockNotification2)).thenReturn(answerRenderer);
		when(mockNotification1.getActivity()).thenReturn(mockActivity);
		when(mockNotification2.getActivity()).thenReturn(mockActivity);
		when(questionRenderer.render(mockNotification1)).thenReturn(questionJson); 
		when(answerRenderer.render(mockNotification2)).thenReturn(answerJson);
		when(mockActivity.getId()).thenReturn(0l);
		ModelAndView actualMAV = notificationController.getQuestionNotifications(mockRequest, mockResponse);
		System.out.println(actualMAV);
		assertEquals("ajax_message_notifications", actualMAV.getViewName());
		assertEquals("{\"latestId\":\"2\",\"data\":[{\"activityId\":1},{\"activityId\":2}]}", actualMAV.getModel().get("messageNots"));

	}
	
	@Test 
	public void getTodoNotifications() throws Exception
	{
		mockRequest.setRemoteUser(CONTACT_NAME_1);
		JsonObject todoJson = new JsonObject(); 
		todoJson.addProperty("activityId", 1);
		List<Notification> notifications = Arrays.asList(mockNotification1); 
		when(mockNotification1.getDiscriminator()).thenReturn(ObjectType.TODO.toString());
		when(mockContactableService.loadContactableByName(CONTACT_NAME_1)).thenReturn(mockPerson);
		when(mockNotificationService.loadUnreadNotificationsForUser(mockPerson,ObjectType.TODO)).thenReturn(notifications);
		when(mockNotificationRendererFactory.getNotificationRenderer(mockNotification1)).thenReturn(genericMockRenderer);
		when(mockNotification1.getActivity()).thenReturn(mockActivity);
		when(genericMockRenderer.render(mockNotification1)).thenReturn(todoJson); 
		when(mockActivity.getId()).thenReturn(0l);
		ModelAndView actualMAV = notificationController.getTodoNotifications(mockRequest, mockResponse);
		System.out.println(actualMAV);
		assertEquals("ajax_message_notifications", actualMAV.getViewName());
		assertEquals("{\"latestId\":\"1\",\"data\":[{\"activityId\":1}]}", actualMAV.getModel().get("messageNots"));

	}
	
	@Test 
	public void getEmailNotifications() throws Exception
	{
		mockRequest.setRemoteUser(CONTACT_NAME_1);
		JsonObject todoJson = new JsonObject(); 
		todoJson.addProperty("activityId", 1);
		List<Notification> notifications = Arrays.asList(mockNotification1); 
		when(mockNotification1.getDiscriminator()).thenReturn(ObjectType.PRIVATEMESSAGE.toString());
		when(mockContactableService.loadContactableByName(CONTACT_NAME_1)).thenReturn(mockPerson);
		when(mockNotificationService.loadUnreadNotificationsForUser(mockPerson,ObjectType.PRIVATEMESSAGE)).thenReturn(notifications);
		when(mockNotificationRendererFactory.getNotificationRenderer(mockNotification1)).thenReturn(genericMockRenderer);
		when(mockNotification1.getActivity()).thenReturn(mockActivity);
		when(genericMockRenderer.render(mockNotification1)).thenReturn(todoJson); 
		when(mockActivity.getId()).thenReturn(0l);
		ModelAndView actualMAV = notificationController.getEmailNotifications(mockRequest, mockResponse);
		System.out.println(actualMAV);
		assertEquals("ajax_message_notifications", actualMAV.getViewName());
		assertEquals("{\"latestId\":\"1\",\"data\":[{\"activityId\":1}]}", actualMAV.getModel().get("messageNots"));

	}
	
	@Test 
	public void getTrackerNotifications() throws Exception
	{
		mockRequest.setRemoteUser(CONTACT_NAME_1);
		JsonObject todoJson = new JsonObject(); 
		todoJson.addProperty("activityId", 1);
		List<Notification> notifications = Arrays.asList(mockNotification1); 
		when(mockNotification1.getDiscriminator()).thenReturn(ObjectType.TRACKER.toString());
		when(mockContactableService.loadContactableByName(CONTACT_NAME_1)).thenReturn(mockPerson);
		when(mockNotificationService.loadUnreadNotificationsForUser(mockPerson,ObjectType.TRACKER)).thenReturn(notifications);
		when(mockNotificationRendererFactory.getNotificationRenderer(mockNotification1)).thenReturn(genericMockRenderer);
		when(mockNotification1.getActivity()).thenReturn(mockActivity);
		when(genericMockRenderer.render(mockNotification1)).thenReturn(todoJson); 
		when(mockActivity.getId()).thenReturn(0l);
		ModelAndView actualMAV = notificationController.getTrackerNotifications(mockRequest, mockResponse);
		System.out.println(actualMAV);
		assertEquals("ajax_message_notifications", actualMAV.getViewName());
		assertEquals("{\"latestId\":\"1\",\"data\":[{\"activityId\":1}]}", actualMAV.getModel().get("messageNots"));

	}

	

}
