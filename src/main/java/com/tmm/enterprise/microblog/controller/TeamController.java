package com.tmm.enterprise.microblog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Team;
import com.tmm.enterprise.microblog.domain.enums.UserRole;
import com.tmm.enterprise.microblog.helper.ControllerHelper;
import com.tmm.enterprise.microblog.security.Account;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.JsonService;

@Controller
@RequestMapping(value = "/team")
public class TeamController {
	private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

	private final static String ERROR_MESSAGE = "Invalid User Entered - Please ensure you have selected a valid, existing user";

	@Autowired
	private AccountService accountService;

	@Autowired
	private ContactableService contactService;

	@Autowired
	private JsonService jsonService;

	public void setJsonService(JsonService jsonService) {
		this.jsonService = jsonService;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService aService) {
		this.accountService = aService;
	}

	public void setContactService(ContactableService contactService) {
		this.contactService = contactService;
	}

	@RequestMapping("/{teamName}")
	public ModelAndView viewTeam(@PathVariable("teamName") String teamName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (teamName.isEmpty()) { // or check that user exists
			logger.debug("Error - No Team name entered when navigating to Team home");
			return ControllerHelper.buildErrorMAV(ERROR_MESSAGE);
		}

		String userName = request.getRemoteUser();
		Account user = getAccountService().loadAccountByUserName(userName);

		Team t = (Team) contactService.loadContactableByName(teamName);
		if (t == null) {
			logger.debug("Error - Team name entered does not exist in system");
			return ControllerHelper.buildErrorMAV(ERROR_MESSAGE);
		}
		JsonArray members = new JsonArray();
		JsonArray management = new JsonArray();
		for (Person p : t.getMembers()) {
			if (UserRole.MANAGER.equals(p.getRole())) {
				management.add(jsonService.convertToJson(p));
			} else {
				members.add(jsonService.convertToJson(p));
			}
		}
		Map<String, Object> model = jsonService.buildUserProfile(user);
		List<Contactable> contacts = contactService.loadAllContactables();
		jsonService.addContactsToModel(contacts, model);
		model.put("members", members);
		model.put("managers", management);
		model.put("teamname", t.getName());
		model.put("teamdesc", t.getDescription());

		// TODO fix this to show team connections only..
		// jsonService.addConnectionsToModel(user, model);

		return new ModelAndView("teamhomepage", model);
	}

	/**
	 * Method to build the org chart from a given team down.
	 * 
	 * @param teamName
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/orgchart/{teamName}")
	public ModelAndView createOrgChart(@PathVariable("teamName") String teamName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (teamName.isEmpty()) { // or check that user exists
			logger.debug("Error - No Team name entered when navigating to Team home");
			return ControllerHelper.buildErrorMAV(ERROR_MESSAGE);
		}

		Team t = (Team) contactService.loadContactableByName(teamName);
		if (t == null) {
			logger.debug("Error - Team name entered does not exist in system");
			return ControllerHelper.buildErrorMAV(ERROR_MESSAGE);
		}

		String userName = request.getRemoteUser();
		Account user = getAccountService().loadAccountByUserName(userName);
		Map<String, Object> model = jsonService.buildUserProfile(user);
		List<Contactable> contacts = contactService.loadAllContactables();
		jsonService.addContactsToModel(contacts, model);
		model.put("team", contactService.buildOrgChart(t));

		return new ModelAndView("teamorgchart", model);
	}

	@RequestMapping("/createTeam")
	public ModelAndView createTeam(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String teamName = request.getParameter("teamName");
		String teamDesc = request.getParameter("teamDesc");
		String parentTeam = request.getParameter("recipientId");
		long parentTeamId = 0l;
		if (parentTeam != null && !"".equals(parentTeam)) {
			parentTeamId = Long.parseLong(parentTeam);
		}

		contactService.createTeam(teamName, parentTeamId, teamDesc);
		Map<String, String> model = Maps.newHashMap();
		return new ModelAndView("ajax_team", model);
	}

	@RequestMapping("/getSettings")
	public ModelAndView getSettings(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String selectedTeam = request.getParameter("selectedTeam");
		long selectedTeamId = Long.parseLong(selectedTeam);
		JsonObject returnObj = new JsonObject();
		Team t = contactService.loadTeam(selectedTeamId);
		JsonArray members = new JsonArray();
		JsonObject teamLead = new JsonObject();
		for (Person p : t.getMembers()) {
			JsonObject o = new JsonObject();
			o.addProperty("id", p.getId());
			o.addProperty("name", p.getName());
			members.add(o);
			if (p.getRole() == UserRole.MANAGER) {
				teamLead.addProperty("id", p.getId());
				teamLead.addProperty("name", p.getName());
			}
		}
		returnObj.add("members", members);
		returnObj.add("teamLead", teamLead);

		JsonObject pTeam = new JsonObject();
		if (t.getParentTeam() != null) {
			pTeam.addProperty("pTeamName", t.getParentTeam().getName());
			pTeam.addProperty("pTeamId", t.getParentTeam().getId());
		}
		returnObj.add("pTeam", pTeam);

		Map<String, String> model = Maps.newHashMap();
		model.put("updates", returnObj.toString());
		return new ModelAndView("ajax_team", model);
	}

	@RequestMapping("/updateTeam")
	@ResponseBody
	//strangely the json submission mechanism seems to stop us using long[]binding directly??
	public ModelAndView updateTeam(@RequestParam Long selectedTeam,@RequestParam Long parentTeam,@RequestParam String team) throws Exception {
		Long[] args = calculateTeamArgs(team);
		contactService.updateTeam(selectedTeam, parentTeam, 0l,args);

		Map<String, String> model = Maps.newHashMap();
		model.put("message", "Team Updated");
		return new ModelAndView("ajax_updateTeam", model);
	}

	private Long[] calculateTeamArgs(String team) {
		String[] teamArgs = team.split(",");
		Long[]args = new Long[teamArgs.length]; 
		int i = 0 ; 
		for(String s : teamArgs)
		{
			long l = 0 ; 
			if(!s.isEmpty())
			{
				args[i++] = Long.valueOf(s);
			}
		}
		return args;
	}
}