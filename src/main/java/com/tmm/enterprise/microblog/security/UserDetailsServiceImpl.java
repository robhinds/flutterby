package com.tmm.enterprise.microblog.security;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tmm.enterprise.microblog.service.AccountService;


public class UserDetailsServiceImpl implements UserDetailsService, InitializingBean{

	private AccountService accountService = null;

	public void setAccountService(AccountService accountService){
		this.accountService = accountService;
	}

	public void afterPropertiesSet() throws Exception{
		Assert.notNull(this.accountService, "An account controller must be set");
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException{
		userName = userName.toLowerCase();
		try{
			//try and load the username entered from the db
			Account account = getAccountService().loadAccountByUserName(userName);

			//check that the username exists
			if (account == null){
				throw new UsernameNotFoundException("Could not find username: " + userName + "in the DB.");
			}

			//recurse through set of roles
			GrantedAuthority[] auths = null;
			try{
				auths = new GrantedAuthority[account.getRoles().size()];
			}catch (Exception ex){
				ex.printStackTrace();
			}
			 
			int count = 0;
			for (Role r : account.getRoles()){
				auths[count++] = new GrantedAuthorityImpl(r.getRole());
			}


			//create application user
			ApplicationUser user = null;
			try{
				user= new ApplicationUser(new Long(account.getId()), userName, account.getPassword(), true, true, true, true, auths);
			}catch (Exception ex){
				ex.printStackTrace();
			}			
			return user;
		}
		catch (Exception e){
			e.printStackTrace();
			throw new UsernameNotFoundException(userName + "not found", e);
		}
	}

	public AccountService getAccountService(){
		return accountService;
	}
}
