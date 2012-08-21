package com.tmm.enterprise.microblog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.domain.Activity;
import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.WorkTask;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.JsonService;
import com.tmm.enterprise.microblog.service.NotificationService;
import com.tmm.enterprise.microblog.service.SearchService;
import com.tmm.enterprise.microblog.service.WorkTaskService;

@Controller
@RequestMapping(value = "/worktask")
public class WorkTaskController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private ContactableService contactService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private JsonService jsonService;
	@Autowired
	private SearchService searchService;
	@Autowired
	private WorkTaskService workTaskService;

	public void setJsonService(JsonService jsonService) {
		this.jsonService = jsonService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setContactService(ContactableService contactService) {
		this.contactService = contactService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public void setWorkTaskService(WorkTaskService workTaskService) {
		this.workTaskService = workTaskService;
	}

	/**
	 * Controller method invoked when a user attempts to create a new Question
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/createWorkTask")
	public ModelAndView createWorkTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		String userName = request.getRemoteUser();
		String assignedTo = request.getParameter("recipientId");
		long assignedToId = Long.parseLong(assignedTo);
		String priority = request.getParameter("priority");
		workTaskService.createWorkTask(title, body, userName, assignedToId, priority);
		Map<String, String> model = Maps.newHashMap();
		return new ModelAndView("ajax_worktask", model);
	}

	/**
	 * Method to build users WorkTask list
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Person currentUser = accountService.getPerson(request);
		Map<String, Object> model = Maps.newHashMap();
		JsonArray raisedTasks = new JsonArray();
		JsonArray assignedTasks = new JsonArray();
		if (currentUser != null) {
			List<WorkTask> raised = workTaskService.loadWorkTasksRaised(currentUser);
			for (WorkTask wt : raised) {
				raisedTasks.add(jsonService.convertToJson(wt));
			}
			List<WorkTask> assigned = workTaskService.loadWorkTasksAssignedTo(currentUser);
			for (WorkTask wt : assigned) {
				assignedTasks.add(jsonService.convertToJson(wt));
			}
		}

		List<WorkTask> latestTasks = workTaskService.getLatestWorkTasks(8);
		JsonArray latest = new JsonArray();
		for (WorkTask wt : latestTasks) {
			JsonObject obj = new JsonObject();
			obj.addProperty("title", wt.getTitle());
			obj.addProperty("id", wt.getId());
			latest.add(obj);
		}

		model.put("tasksRaised", raisedTasks);
		model.put("tasksAssigned", assignedTasks);
		model.put("latestTasks", latest);

		jsonService.addUserInfoToModel(accountService.getAccount(request), model);
		List<Contactable> contacts = contactService.loadAllContactables();
		jsonService.addContactsToModel(contacts, model);
		return new ModelAndView("workTaskList", model);
	}

	/**
	 * Method to build the detailed question page
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/detail/{taskId}")
	public ModelAndView detail(@PathVariable("taskId") long taskId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Person currentUser = accountService.getPerson(request);
		notificationService.markAsRead(currentUser, taskId);
		WorkTask t = workTaskService.loadWorkTask(taskId);
		JsonObject task = jsonService.convertToJson(t);

		List<Activity> similarTasks = searchService.searchTasksByTitle(t.getTitle());
		JsonArray similar = new JsonArray();
		for (Activity wt : similarTasks) {
			if (!wt.equals(t)) {
				JsonObject obj = new JsonObject();
				obj.addProperty("title", wt.getTitle());
				obj.addProperty("id", wt.getId());
				similar.add(obj);
			}
		}

		Map<String, Object> model = Maps.newHashMap();
		model.put("task", task);
		model.put("similar", similar);
		jsonService.addUserInfoToModel(accountService.getAccount(request), model);
		List<Contactable> contacts = contactService.loadAllContactables();
		jsonService.addContactsToModel(contacts, model);
		return new ModelAndView("workTaskDetail", model);
	}

	/**
	 * Controller method invoked when a user attempts to add an update to a work
	 * task
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateWorkTask")
	public ModelAndView updateWorkTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String state = request.getParameter("state");
		String body = request.getParameter("comment");
		String userName = request.getRemoteUser();
		String assignedTo = request.getParameter("recipientId");
		long assignedToId = Long.parseLong(assignedTo);
		String priority = request.getParameter("priority");
		String task = request.getParameter("taskId");
		long taskId = Long.parseLong(task);
		workTaskService.updateWorkTask(state, body, userName, assignedToId, priority, taskId);

		Map<String, String> model = Maps.newHashMap();
		return new ModelAndView("ajax_worktask", model);
	}

}