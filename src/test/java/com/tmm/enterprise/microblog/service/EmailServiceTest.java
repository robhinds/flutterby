package com.tmm.enterprise.microblog.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import com.tmm.enterprise.microblog.core.exception.ButterflyException;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.enums.UserRole;
import com.tmm.enterprise.microblog.security.Account;
import com.tmm.enterprise.microblog.security.Role;

public class EmailServiceTest {

	private MessageService emailService = new MessageService();
	private AccountService accountService;
	private ContactableService contactService;
	private EntityManager entitManager;

	@Before
	public void before() {
		accountService = mock(AccountService.class);
		contactService = mock(ContactableService.class);
		entitManager = mock(EntityManager.class);
		emailService.setAccountService(accountService);
		emailService.setContactService(contactService);
		emailService.setEntityManager(entitManager);
	}

	@Test
	public void testSendEmail() {
		Account acc = new Account();
		acc.setId(1l);
		acc.setUserName("rob");
		Role r = new Role();
		r.setRole(UserRole.MEMBER.toString());
		acc.addRole(r);

		Person p = new Person();
		p.setRole(UserRole.MEMBER);
		p.setLinkedAccount(acc);
		acc.setUserProfile(p);

		// mock out calls to other service classes
		when(accountService.loadAccountByUserName("rob")).thenReturn(acc);
		when(contactService.loadPerson(1l)).thenReturn(p);

		try {
			emailService.sendEmail("rob", "test Email body..", "1");
		} catch (ButterflyException e) {
			fail("Unexpected Exception");
		}

		assertEquals(1, p.getReceivedMessages().size());
		assertEquals("test Email body..", p.getReceivedMessages().get(0).getDetails());
	}

}
