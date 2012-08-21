package com.tmm.enterprise.microblog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.servlet.view.RedirectView;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.PrivateMessage;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.MessageService;
import com.tmm.enterprise.microblog.service.JsonService;


public class EmailController extends MultiActionController implements InitializingBean 
{
	private static final String QUALIFIED_REDIRECT = "../";
	private static final String NONQUALIFIED_REDIRECT = "./";
	
	/**
	 * Service Classes AutoWired in
	 */
	@Autowired
	private AccountService accountService;
	@Autowired
	private ContactableService contactService;
	@Autowired
	private MessageService emailService;
	@Autowired
	private JsonService jsonService;
	

	public void setJsonService(JsonService jsonService) {
		this.jsonService = jsonService;
	}
	

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setContactService(ContactableService cService) {
		this.contactService = cService;
	}
	
	public void setEmailService(MessageService emailService) {
		this.emailService = emailService;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {}

	/**
	 * Default handler to handle any incorrect urls - just forwards to home for now
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public ModelAndView handleNoSuchRequestHandlingMethod(NoSuchRequestHandlingMethodException ex, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return new ModelAndView(new RedirectView(QUALIFIED_REDIRECT));
	}
	
	
	/**
	 * Method to build users inbox
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> model = getJsonMessagesForPerson(request);
		return new ModelAndView("emailFolder", "model", model);
	}

	
	private Map<String, Object> getJsonMessagesForPerson(
			HttpServletRequest request) {
		Person currentUser = accountService.getPerson(request);
		Map<String, Object> model = Maps.newHashMap();
		JsonArray array = new JsonArray();
		if (currentUser!=null){
			List<PrivateMessage> emails = currentUser.getReceivedMessages();
			for (PrivateMessage pm : emails){
				array.add(jsonService.convertToJson(pm));
			}
		}
		model.put("emails", array);
		return model;
	}
	
	@Transactional
	public ModelAndView messageDashboard(HttpServletRequest request, HttpServletResponse response)throws Exception{
		Map<String, Object> model = getJsonMessagesForPerson(request);
		return new ModelAndView("messageDash","model",model);
		
	}
	
	
	/**
	 * Method to load view to allow user to compose new message
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public ModelAndView compose(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> model = Maps.newHashMap();
		JsonArray array = new JsonArray();
		List<Person> contacts = contactService.loadAllPersons();
		for (Person p : contacts){
			array.add(jsonService.convertToJson(p));
		}
		model.put("contacts", array);
		return new ModelAndView("emailCompose", "model", model);
	}
	
	
	
	/**
	 * Method to send email message
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView sendEmail(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String msg = request.getParameter("msg");
		String recipId = request.getParameter("recipient");
		emailService.sendEmail(request.getRemoteUser(), msg, recipId);
		return new ModelAndView("ajax_email");
	}

}