package com.tmm.enterprise.microblog.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.enums.UserRole;
import com.tmm.enterprise.microblog.security.Account;
import com.tmm.enterprise.microblog.security.ApplicationUser;
import com.tmm.enterprise.microblog.security.Role;
import com.tmm.enterprise.microblog.security.dao.AccountDAO;
import com.tmm.enterprise.microblog.security.dao.RoleDAO;

@Service("accountService")
public class AccountService {
	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private RoleDAO roleDAO;

	public AccountService() {
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Account> findAllAccounts() {
		return getAccountDAO().find();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Account loadAccount(long id) {
		return getAccountDAO().read(Account.class, new Long(id));
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Account loadAccountByUserName(String userName) {
		return getAccountDAO().loadAccountByUserName(userName);
	}

	@Transactional
	public void removeAccount(long id) {
		getAccountDAO().delete(
				getAccountDAO().read(Account.class, new Long(id)));
	}

	@Transactional
	public Account storeAccount(Account account) {
		return getAccountDAO().merge(account);
	}

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO contactDAO) {
		accountDAO = contactDAO;
	}

	public Account createNewBatchUser(String name, String email, String password) {
		return createNewBatchUser(name, email, password, null, null);
	}

	public Account createNewAdminUser(String name, String email, String password) {
		return createNewUser(name, email, password, null, null, Role.ROLE_ADMIN);
	}

	public Account createNewNormalUser(String name, String email,
			String password) {
		return createNewUser(name, email, password, null, null, Role.ROLE_USER);
	}

	/**
	 * method to create a new useraccount for the system
	 * 
	 * @param name
	 * @param email
	 * @param password
	 */
	@Transactional
	public Account createNewUser(String name, String email, String password,
			String firstName, String lastName, String role) {
		Account account = loadAccountByUserName(name);
		if (account == null) {
			account = new Account();
			Role r = loadOrCreateRole(role);
			account.addRole(r);
			account.setUserName(name);
			account.setEmail(email);
			account.setFirstName(firstName);
			account.setLastName(lastName);
			Person userProf = new Person();
			userProf.setRole(UserRole.MEMBER);
			userProf.setLinkedAccount(account);
			account.setUserProfile(userProf);

			getAccountDAO().persist(account);

			// now update password once we have the account created (we need the
			// ID to have been generated)
			account.setAndEncodePassword(password);
			// account = getAccountDAO().merge(account);
		}

		return account;
	}

	@Transactional
	public Account createNewFullAdminUser(String name, String email,
			String password) {
		return createNewFullAdminUser(name, email, password, null, null);
	}

	/**
	 * method to create a new useraccount for the system
	 * 
	 * @param name
	 * @param email
	 * @param password
	 */
	@Transactional
	public Account createNewFullAdminUser(String name, String email,
			String password, String firstName, String lastName) {
		Account account = loadAccountByUserName(name);
		if (account == null) {
			account = new Account();
			Role r = loadOrCreateRole(Role.ROLE_ADMIN);
			account.addRole(r);
			account.setUserName(name);
			account.setEmail(email);
			account.setFirstName(firstName);
			account.setLastName(lastName);
			Person userProf = new Person();
			userProf.setRole(UserRole.MEMBER);
			userProf.setLinkedAccount(account);
			account.setUserProfile(userProf);

			getAccountDAO().persist(account);

			// now update password once we have the account created (we need the
			// ID to have been generated)
			account.setAndEncodePassword(password);
			// getAccountDAO().merge(account);
		}

		return account;
	}

	@Transactional
	public Account createNewBatchUser(String name, String email,
			String password, String firstName, String lastName) {
		Account account = loadAccountByUserName(name);
		if (account == null) {
			account = new Account();
			Role r = loadOrCreateRole(Role.ROLE_ADMIN);
			account.addRole(r);
			account.setUserName(name);
			account.setEmail(email);
			account.setFirstName(firstName);
			account.setLastName(lastName);

			getAccountDAO().persist(account);

			// now update password once we have the account created (we need the
			// ID to have been generated)
			account.setAndEncodePassword(password);
			// getAccountDAO().merge(account);
		}

		return account;
	}

	/**
	 * method that tries to load a Role, if no Role exists then it creates it
	 * 
	 * @param string
	 *            - the permission level
	 * @return the new Role
	 */
	private Role loadOrCreateRole(String authority) {
		Role r = getRoleDAO().loadRoleByAuthority(authority);
		if (r == null) {
			System.out.println("creating role " + authority);
			// create new role
			r = new Role();
			r.setRole(authority);
			getRoleDAO().persist(r);
		}
		return r;
	}

	/**
	 * @param roleDAO
	 *            the roleDAO to set
	 */
	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	/**
	 * @return the roleDAO
	 */
	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	/**
	 * Method to retrieve the current user's account from a HTTPRequest
	 * 
	 * @param request
	 * @return
	 */
	public Account getAccount(HttpServletRequest request) {
		String userName = request.getRemoteUser();
		Account acc = loadAccountByUserName(userName);
		return acc;
	}

	/**
	 * Method to retrieve the current user profile from a HTTPRequest
	 * 
	 * @param request
	 * @return
	 */
	public Person getPerson(HttpServletRequest request) {
		Account acc = getAccount(request);
		Person person = acc.getUserProfile();
		return person;
	}

	@Transactional
	public void setCredentials() {
		Account account = loadAccountByUserName("admin");
		if (account == null) {
			account = createNewAdminUser("admin", "", "admin");
		}
		GrantedAuthority[] auths = new GrantedAuthority[1];
		auths[0] = new GrantedAuthorityImpl("ROLE_ADMIN");
		ApplicationUser user = new ApplicationUser(new Long(account.getId()),
				account.getUserName(), account.getPassword(), true, true, true,
				true, auths);
		Authentication auth = new TestingAuthenticationToken(user, "ignored",
				auths);
		auth.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	public void clearCredentials() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}
}