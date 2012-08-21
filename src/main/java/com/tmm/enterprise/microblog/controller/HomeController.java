package com.tmm.enterprise.microblog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.helper.UserHelper;
import com.tmm.enterprise.microblog.security.Account;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.JsonService;

@Controller
public class HomeController {
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

	public void setContactService(ContactableService contactService) {
		this.contactService = contactService;
	}

	public void setAccountService(AccountService aService) {
		this.accountService = aService;
	}

	/**
	 * This method is called by the home page only - determines if anonymous
	 * request and then either displays welcome info or login page
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView anonhome(HttpServletRequest request) throws Exception {
		boolean isAnon = UserHelper.isAnonymousUser(request);
		if (isAnon) {
			return new ModelAndView("anonhomepage");
		} else {
			String userName = request.getRemoteUser();
			Account user = getAccountService().loadAccountByUserName(userName);
			List<Contactable> contacts = contactService.loadAllContactables();
			Map<String, Object> model = jsonService.buildUserProfile(user);
			jsonService.addContactsToModel(contacts, model);
			jsonService.addConnectionsToModel(user, model);
			return new ModelAndView("userhomepage", model);
		}
	}

	@RequestMapping
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("register");
	}

	/**
	 * method to allow new users to signup
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	public ModelAndView signup(@RequestParam String username,@RequestParam String pword1,@RequestParam String pword2,@RequestParam String email1,@RequestParam String email2) throws Exception {
		getAccountService().setCredentials();
		getAccountService().createNewNormalUser(username, email1, pword1);
		getAccountService().clearCredentials();

		return new ModelAndView(new RedirectView(""));
	}
}
