package com.tmm.enterprise.microblog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.core.exception.ButterflyException;
import com.tmm.enterprise.microblog.domain.Activity;
import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.enums.ObjectType;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.JsonService;
import com.tmm.enterprise.microblog.service.SearchService;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

	@Autowired
	private JsonService jsonService;
	@Autowired
	private SearchService searchService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ContactableService contactService;

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

	@RequestMapping("/question")
	public ModelAndView searchQuestions(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = activitySearch(request, ObjectType.QUESTION);
		model.put("context", "question");
		return new ModelAndView("searchResults", model);
	}

	@RequestMapping("/todo")
	public ModelAndView searchTodos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = activitySearch(request, ObjectType.TODO);
		model.put("context", "todo");
		return new ModelAndView("searchResults", model);
	}

	@RequestMapping("/worktask")
	public ModelAndView searchWorkTasks(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = activitySearch(request, ObjectType.TRACKER);
		model.put("context", "worktask");
		return new ModelAndView("searchResults", model);
	}

	@RequestMapping("/all")
	public ModelAndView searchActivities(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = activitySearch(request, ObjectType.PROJECT);
		return new ModelAndView("searchResults", model);
	}

	/**
	 * Generic method to perform a search for an activity
	 * 
	 * @param request
	 * @param activityType
	 * @return
	 * @throws ButterflyException
	 */
	private Map<String, Object> activitySearch(HttpServletRequest request, ObjectType activityType) throws ButterflyException {
		String searchTerm = request.getParameter("searchTerm");
		List<Activity> results = searchService.searchActivityByTitle(searchTerm, activityType);
		JsonArray jsonResults = new JsonArray();
		for (Activity act : results) {
			JsonObject obj = new JsonObject();
			obj.addProperty("title", act.getTitle());
			setContext(act, obj);
			jsonResults.add(obj);
		}

		Map<String, Object> model = Maps.newHashMap();
		model.put("results", jsonResults);
		jsonService.addUserInfoToModel(accountService.getAccount(request), model);
		List<Contactable> contacts = contactService.loadAllContactables();
		jsonService.addContactsToModel(contacts, model);
		return model;
	}

	private void setContext(Activity act, JsonObject obj) {
		switch (act.getObjectType()) {
		case TODO:
			obj.addProperty("id", act.getId());
			obj.addProperty("context", "todo");
			obj.addProperty("styleClass", "activityTodo");
			break;
		case TRACKER:
			obj.addProperty("id", act.getId());
			obj.addProperty("context", "worktask");
			obj.addProperty("styleClass", "activityTracker");
			break;
		case QUESTION:
			obj.addProperty("id", act.getId());
			obj.addProperty("context", "question");
			obj.addProperty("styleClass", "activityQuestion");
			break;
		case STATUS:
			obj.addProperty("id", act.getId());
			obj.addProperty("context", "status");
			obj.addProperty("styleClass", "activityStatus");
			break;
		}

	}

}