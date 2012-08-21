package com.tmm.enterprise.microblog.util;

import org.springframework.transaction.annotation.Transactional;

import com.tmm.enterprise.microblog.domain.Notification;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.ToDo;
import com.tmm.enterprise.microblog.security.Account;

public class CreateNotification extends BatchProcess {

	@Transactional
	public void execute() throws Exception {
		setCredentials();
		Person belongsTo = getContactableService().loadPerson(50);

		ToDo todo = new ToDo();
		todo.setAssignedTo(belongsTo);
		todo.setDetails("its a todo item!");
		todo.setTitle("new todo..");
		getActivityService().getEntityManager().persist(todo); 
		Account acc = getAccount();

		// TODO persist activity here..

		Notification notification = new Notification();
		notification.setActivity(todo);

		getNotificationService().createNotification(notification);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		new CreateNotification().execute();
	}
}
