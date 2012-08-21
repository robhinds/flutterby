package com.tmm.enterprise.microblog.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.core.exception.ButterflyException;
import com.tmm.enterprise.microblog.domain.Notification;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.enums.ObjectType;
import com.tmm.enterprise.microblog.renderer.NotificationRenderer;
import com.tmm.enterprise.microblog.renderer.NotificationRendererFactory;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.NotificationService;

public class NotificationController extends MultiActionController implements InitializingBean {

	@Autowired
	private NotificationService notificationService;
	@Autowired
	private ContactableService contactableService;
	@Autowired
	private NotificationRendererFactory rendererFactory; 


	public void setRendererFactory(NotificationRendererFactory rendererFactory) {
		this.rendererFactory = rendererFactory;
	}

	public void setContactableService(ContactableService contactableService) {
		this.contactableService = contactableService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	private NotificationRenderer notificationRenderer;

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	/**
	 * Controller Method that loads the Notification objects for Questions for
	 * the current logged in user
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getQuestionNotifications(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return getNotifications(request,ObjectType.QUESTION,ObjectType.ANSWER);
	}

	/**
	 * Controller Method that loads the Notification objects for ToDos for the
	 * current logged in user
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getTodoNotifications(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return getNotifications(request,ObjectType.TODO);
	}

	/**
	 * Controller Method that loads the Notification objects for PrivateMessages
	 * for the current logged in user
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getEmailNotifications(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return getNotifications(request,ObjectType.PRIVATEMESSAGE);
	}



	/**
	 * Controller Method that loads the Notification objects for WorkTickets for
	 * the current logged in user
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getTrackerNotifications(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return getNotifications(request,ObjectType.TRACKER);
	}
	
	private ModelAndView getNotifications(HttpServletRequest request,ObjectType... notificationTypes)
			throws ButterflyException {
		Person user = (Person) contactableService.loadContactableByName(request.getRemoteUser());
		String latestIdStr = request.getParameter("latestUpdateId");
		Long latestId = 0l; // default to 0 so it is never found if no latest ID
		// provided
		if (latestIdStr != null) {
			latestId = Long.parseLong(latestIdStr);
		}
		boolean startRecording = (latestId == 0l);

		JsonArray nots = new JsonArray();
		for (Notification n : notificationService.loadUnreadNotificationsForUser(user, notificationTypes)) {
			if (startRecording) {
				notificationRenderer = rendererFactory.getNotificationRenderer(n);
				nots.add(notificationRenderer.render(n));
			}

			if (n.getActivity().getId().equals(latestId)) {
				startRecording = true;
			}
		}

		JsonObject returnObj = new JsonObject();
		if (nots.size() > 0) {
			returnObj.addProperty("latestId", nots.get(nots.size() - 1).getAsJsonObject().get("activityId").getAsString());
		} else {
			returnObj.addProperty("latestId", latestId);
		}
		returnObj.add("data", nots);
		Map<String, String> model = Maps.newHashMap();
		model.put("messageNots", returnObj.toString());
		return new ModelAndView("ajax_message_notifications", model);
	}
}
