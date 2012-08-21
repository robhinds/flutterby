package com.tmm.enterprise.microblog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Team;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.JsonService;

@Controller
public class PeopleController  {

	/**
	 * Service Classes AutoWired in
	 */
	@Autowired
	private ContactableService contactService;

	@Autowired
	private JsonService jsonService;

	public void setJsonService(JsonService jsonService) {
		this.jsonService = jsonService;
	}

	public void setContactService(ContactableService cService) {
		this.contactService = cService;
	}

	/**
	 * Method to build the person directory
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView loadDirectory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonArray array = new JsonArray();
		List<Team> teams = contactService.loadAllTeams();
		List<Person> checkedPeople = new ArrayList<Person>();
		checkedPeople.addAll(contactService.loadAllPersons());
		for (Team t : teams) {
			checkedPeople.removeAll(t.getMembers());
			array.add(jsonService.deepConvertToJson(t));
		}

		// check for people without teams
		Team dummyT = new Team();
		dummyT.setName("No Team");
		dummyT.setMembers(checkedPeople);
		array.add(jsonService.deepConvertToJson(dummyT));
		Map<String, Object> model = Maps.newHashMap();
		model.put("directory", array.toString());
		return new ModelAndView("ajax_people", model);
	}

	@RequestMapping
	public ModelAndView connect(@RequestParam Long connectId,HttpServletRequest request) throws Exception {
		String userName = request.getRemoteUser();
		contactService.connect(userName, connectId);
		return new ModelAndView("ajax_connect");
	}
}