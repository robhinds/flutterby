package com.tmm.enterprise.microblog.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmm.enterprise.microblog.security.Account;
import com.tmm.enterprise.microblog.security.Role;

public class AccountServiceDbTest extends AbstractServiceTest {

	@Autowired
	AccountService service;

	@Test
	public void testFindAllAccounts() {
		List<Account> accs = service.findAllAccounts();
		assertEquals("Check DB is empty first", 1, accs.size()); // always the
																	// batch
																	// test
																	// account
																	// created -
																	// needed
																	// for
																	// persistence
		Account a = new Account();
		a.setUserName("robb");
		a.setPassword("password");
		service.storeAccount(a);
		accs = service.findAllAccounts();
		assertEquals("check Account has been created", 2, accs.size());
	}

	@Test
	public void testLoadAccount() {
		List<Account> accs = service.findAllAccounts();
		assertTrue(accs.size() > 0);
		Account original = accs.get(0);
		Account a = service.loadAccount(original.getId()); // this is will load
															// the test account
		assertNotNull(a);
		assertEquals(original.getUserName(), a.getUserName());
	}

	@Test
	public void testLoadAccountByUserName() {
		Account a = service.loadAccountByUserName("test");
		assertNotNull(a);
		assertEquals("test", a.getUserName());
	}

	@Test
	public void testCreateNewAdminUserStringStringString() {
		service.createNewAdminUser("robbo", "email@email.com", "password");
		Account a = service.loadAccountByUserName("robbo");
		assertNotNull(a);
		assertEquals("email@email.com", a.getEmail());
	}

	@Test
	public void testCreateNewAdminUserStringStringStringStringString() {
		service.createNewUser("robbo", "email@email.com", "password", "rob", "hinds", Role.ROLE_ADMIN);
		Account a = service.loadAccountByUserName("robbo");
		assertNotNull(a);
		assertEquals("email@email.com", a.getEmail());
		assertEquals("rob", a.getFirstName());
		assertEquals("hinds", a.getLastName());
	}

	@Test
	public void testCreateNewFullAdminUserStringStringString() {
		service.createNewFullAdminUser("robbo", "email@email.com", "password");
		Account a = service.loadAccountByUserName("robbo");
		assertNotNull(a);
		assertEquals("email@email.com", a.getEmail());
		assertNotNull(a.getUserProfile());
	}

	@Test
	public void testCreateNewFullAdminUserStringStringStringStringString() {
		service.createNewFullAdminUser("robbo", "email@email.com", "password", "rob", "hinds");
		Account a = service.loadAccountByUserName("robbo");
		assertNotNull(a);
		assertEquals("email@email.com", a.getEmail());
		assertEquals("rob", a.getFirstName());
		assertEquals("hinds", a.getLastName());
		assertNotNull(a.getUserProfile());
	}

}
