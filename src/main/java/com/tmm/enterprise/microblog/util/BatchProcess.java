package com.tmm.enterprise.microblog.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tmm.enterprise.microblog.security.Account;
import com.tmm.enterprise.microblog.security.ApplicationUser;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ActivityService;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.MessageService;
import com.tmm.enterprise.microblog.service.NotificationService;
import com.tmm.enterprise.microblog.service.SearchService;
import com.tmm.enterprise.microblog.service.StatusService;

public class BatchProcess {
	private static final String BATCH_USER_NAME = "batch";

	private ApplicationContext applicationContext = null;
	private StatusService statusService;

	private SearchService searchService;

	private AccountService accountService;

	private BatchService batchService;

	private ActivityService activityService;

	private ContactableService contactService;

	private MessageService messageService;

	private NotificationService notificationService;

	public BatchProcess() {
	}

	public Account getAccount() {
		return getAccountService().loadAccountByUserName(BATCH_USER_NAME);
	}

	public void setCredentials() {
		Account account = getAccountService().loadAccountByUserName(BATCH_USER_NAME);

		GrantedAuthority[] auths = new GrantedAuthority[1];
		auths[0] = new GrantedAuthorityImpl("ROLE_USER");

		ApplicationUser user = new ApplicationUser(new Long(account.getId()), account.getUserName(), account.getPassword(), true, true, true, true,
				auths);

		Authentication auth = new TestingAuthenticationToken(user, "ignored", auths);

		auth.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	public BeanFactory getApplicationContext() {
		if (applicationContext == null) {
			applicationContext = new ClassPathXmlApplicationContext(new String[] { "classpath:/META-INF/spring/beanRefContext.xml" });
		}
		return (BeanFactory) applicationContext.getBean("model.context");
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public StatusService getStatusService() {
		if (statusService == null) {
			statusService = (StatusService) getApplicationContext().getBean("statusService");
		}
		return statusService;
	}

	public SearchService getSearchService() {
		if (searchService == null) {
			searchService = (SearchService) getApplicationContext().getBean("searchService");
		}
		return searchService;
	}

	public ContactableService getContactableService() {
		if (contactService == null) {
			contactService = (ContactableService) getApplicationContext().getBean("contactableService");
		}
		return contactService;
	}

	public ActivityService getActivityService() {
		if (activityService == null) {
			activityService = (ActivityService) getApplicationContext().getBean("activityService");
		}
		return activityService;
	}

	public AccountService getAccountService() {
		if (accountService == null) {
			accountService = (AccountService) getApplicationContext().getBean("accountService");
		}
		return accountService;
	}

	public BatchService getBatchService() {
		if (batchService == null) {
			batchService = (BatchService) getApplicationContext().getBean("batchService");
		}
		return batchService;
	}

	public MessageService getMessageService() {
		if (messageService == null) {
			messageService = (MessageService) getApplicationContext().getBean("messageService");
		}
		return messageService;
	}

	public NotificationService getNotificationService() {
		if (notificationService == null) {
			notificationService = (NotificationService) getApplicationContext().getBean("notificationService");
		}
		return notificationService;
	}
}
