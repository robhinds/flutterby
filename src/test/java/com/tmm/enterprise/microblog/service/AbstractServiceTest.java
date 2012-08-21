package com.tmm.enterprise.microblog.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.enterprise.microblog.security.Account;
import com.tmm.enterprise.microblog.security.ApplicationUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/test-applicationContext.xml" })
@Transactional
public abstract class AbstractServiceTest {

	@Autowired
	AccountService accountService;

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Before
	public void createAccount() {
		Account account = accountService.createNewBatchUser("test", "", "");
		GrantedAuthority[] auths = new GrantedAuthority[1];
		auths[0] = new GrantedAuthorityImpl("ROLE_USER");
		ApplicationUser user = new ApplicationUser(new Long(account.getId()),
				account.getUserName(), account.getPassword(), true, true, true,
				true, auths);
		Authentication auth = new TestingAuthenticationToken(user, "ignored",
				auths);
		auth.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
}
