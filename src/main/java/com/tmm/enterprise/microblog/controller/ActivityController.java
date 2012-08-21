package com.tmm.enterprise.microblog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.domain.Activity;
import com.tmm.enterprise.microblog.domain.Notification;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.security.Account;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ActivityService;
import com.tmm.enterprise.microblog.service.JsonService;
import com.tmm.enterprise.microblog.service.NotificationService;

/**
 * @author robert.hinds
 * 
 *         Controller class to handle interactions with Activities - primarily
 *         concerned with building activity feeds
 */
@Controller
public class ActivityController{
	@Autowired
	private ActivityService activityService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private JsonService jsonService;

	public void setJsonService(JsonService jsonService) {
		this.jsonService = jsonService;
	}

	/**
	 * @param modelService
	 *            the modelService to set
	 */
	public void setActivityService(ActivityService aService) {
		this.activityService = aService;
	}

	public void setAccountService(AccountService aService) {
		this.accountService = aService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	/**
	 * Method loads the latest statuses in the system and returns to the view to
	 * display in the UI
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView latestActivity(@RequestParam(defaultValue="0") Integer latestId,@RequestParam(defaultValue="0") Integer pageNum,HttpServletRequest request) throws Exception {
		Person currentUser = accountService.getPerson(request);
		List<Notification> latest = notificationService.loadNotificationsForUser(currentUser, pageNum);
		JsonObject returnObj = new JsonObject();
		if (!latest.isEmpty()) {
			JsonArray array = new JsonArray();
			boolean startRecording = (latestId == 0l);
			for (int i = latest.size() - 1; i >= 0; i--) {
				if (latest.get(i).getActivity() == null) {
					continue;
				}
				Activity a = latest.get(i).getActivity();
				if (startRecording) {
					JsonObject o = jsonService.convertToJson(a);
					o.addProperty("isOwner", currentUser.equals(a.getRaisedBy()));
					array.add(o);
				}

				// check whether to update status from here
				if (a.getId().equals(latestId)) {
					startRecording = true;
				}
			}

			returnObj.add("statuses", array);
			returnObj.addProperty("latestId", latest.get(0).getActivity().getId());
		}

		Map<String, String> model = Maps.newHashMap();
		model.put("statuses", returnObj.toString());
		return new ModelAndView("ajax_status", model);
	}

	/**
	 * Method loads the latest statuses in the system and returns to the view to
	 * display in the UI
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView latestUserActivity(@RequestParam String userName,@RequestParam(defaultValue="0") Long latestId,@RequestParam Integer pageNum,HttpServletRequest request) throws Exception {
		Account acc = accountService.loadAccountByUserName(userName);
		Person currentUser = acc.getUserProfile();
		List<Activity> latest = activityService.loadLatestPublicStatus(currentUser, pageNum);
		JsonObject returnObj = new JsonObject();
		if (!latest.isEmpty()) {
			JsonArray array = new JsonArray();
			boolean startRecording = (latestId == 0l);
			for (int i = latest.size() - 1; i >= 0; i--) {

				Activity a = latest.get(i);
				if (startRecording) {
					JsonObject o = jsonService.convertToJson(a);
					o.addProperty("isOwner", true);
					array.add(jsonService.convertToJson(a));
				}

				// check whether to update status from here
				if (a.getId().equals(latestId)) {
					startRecording = true;
				}
			}

			returnObj.add("statuses", array);
			returnObj.addProperty("latestId", latest.get(0).getId());
		}

		Map<String, String> model = Maps.newHashMap();
		model.put("statuses", returnObj.toString());
		return new ModelAndView("ajax_status", model);
	}
}