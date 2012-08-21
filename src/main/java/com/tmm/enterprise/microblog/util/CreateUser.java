package com.tmm.enterprise.microblog.util;

import org.springframework.transaction.annotation.Transactional;

public class CreateUser extends BatchProcess {

	@Transactional
	public void execute() throws Exception {
		getAccountService().createNewBatchUser("admin", "", "admin");
		getAccountService().createNewBatchUser("batch", "", "");
		System.out.println("User created!");
	}

	@Transactional
	public void createFullUser(String username, String password) throws Exception {
		setCredentials();
		getAccountService().createNewFullAdminUser(username, "", password);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// new CreateUser().execute();
		// new CreateUser().createFullUser("rob", "");
		// new CreateUser().createFullUser("jamie", "");
		//new CreateUser().createTeam();
		new CreateUser().createFullUser("mike", "");
		new CreateUser().createFullUser("Dan", "");
		new CreateUser().createFullUser("Mark", "");
		new CreateUser().createFullUser("Anna", "");
		new CreateUser().createFullUser("Victoria", "");
		new CreateUser().createFullUser("Alex", "");
		new CreateUser().createFullUser("Neil", "");
		new CreateUser().createFullUser("Trevor", "");
	}

	@Transactional
	private void createTeam() {
		setCredentials();
		getBatchService().setAllTeams("Development");
	}
}
