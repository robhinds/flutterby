package com.tmm.enterprise.microblog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.security.Account;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.JsonService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private ContactableService contactService;
	@Autowired
	private JsonService jsonService;

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setJsonService(JsonService jsonService) {
		this.jsonService = jsonService;
	}

	public void setContactService(ContactableService contactService) {
		this.contactService = contactService;
	}

	/**
	 * Controller method invoked when a user attempts to create a new Question
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/settings")
	public ModelAndView loadAdminPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userName = request.getRemoteUser();
		Account user = accountService.loadAccountByUserName(userName);
		Map<String, Object> model = jsonService.buildUserProfile(user);
		List<Contactable> contacts = contactService.loadAllContactables();
		jsonService.addContactsToModel(contacts, model);
		return new ModelAndView("adminPage", model);
	}

}