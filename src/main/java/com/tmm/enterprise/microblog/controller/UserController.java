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
import org.springframework.web.servlet.ModelAndView;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.helper.ControllerHelper;
import com.tmm.enterprise.microblog.security.Account;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.JsonService;

@Controller
@RequestMapping(value = "/user")
public class UserController {
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

	@RequestMapping("/{userName}")
	public ModelAndView createTodo(@PathVariable("userName") String userName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (userName.isEmpty()) { // or check that user exists
			logger.debug("Error - No Username entered when navigating to user home");
			return ControllerHelper.buildErrorMAV(ERROR_MESSAGE);
		}

		// check if this is the current user's page being browsed
		Account viewedUser = getAccountService().loadAccountByUserName(userName);
		if (request.getRemoteUser().equals(userName)) {
			Map<String, Object> model = jsonService.buildUserProfile(viewedUser);
			List<Contactable> contacts = contactService.loadAllContactables();
			jsonService.addContactsToModel(contacts, model);
			jsonService.addConnectionsToModel(viewedUser, model);
			return new ModelAndView("userhomepage", model);
		} else {
			if (viewedUser == null) {
				logger.debug("Error - Username entered does not exist in system");
				return ControllerHelper.buildErrorMAV(ERROR_MESSAGE);
			}

			Map<String, Object> model = jsonService.buildUserProfile(viewedUser);
			List<Contactable> contacts = contactService.loadAllContactables();
			jsonService.addContactsToModel(contacts, model);
			jsonService.addConnectionsToModel(viewedUser, model);
			model.put("userName", userName);
			return new ModelAndView("otheruserhomepage", model);
		}
	}

}