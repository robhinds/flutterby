package com.tmm.enterprise.microblog.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.enterprise.microblog.core.dao.WorkTaskDAO;
import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.TicketUpdate;
import com.tmm.enterprise.microblog.domain.WorkTask;
import com.tmm.enterprise.microblog.domain.WorkTask.Priorities;
import com.tmm.enterprise.microblog.domain.WorkTask.State;
import com.tmm.enterprise.microblog.security.Account;

@Service("workTaskService")
@Repository
@Transactional
public class WorkTaskService {
	private EntityManager entityManager;

	@Autowired
	private AccountService accountService;
	@Autowired
	private ContactableService contactService;

	@Autowired
	private WorkTaskDAO workTaskDao;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setContactService(ContactableService contactService) {
		this.contactService = contactService;
	}

	public void setWorkTaskDAO(WorkTaskDAO workTaskDao) {
		this.workTaskDao = workTaskDao;
	}

	@Transactional
	public void createWorkTask(WorkTask wt) {
		workTaskDao.persist(wt);
	}

	@Transactional
	public WorkTask loadWorkTask(long id) {
		return getEntityManager().find(WorkTask.class, id);
	}

	@Transactional
	public void createWorkTask(String title, String description, String currentUserName, long assignedToId, String priority) {
		Account acc = accountService.loadAccountByUserName(currentUserName);
		Person currentUser = acc.getUserProfile();
		if (currentUser != null) {
			Contactable contact = contactService.loadContactable(assignedToId);
			WorkTask wt = new WorkTask();
			wt.setTitle(title);
			wt.setDetails(description);
			wt.setPriority(WorkTask.Priorities.valueOf(priority));
			wt.setRaisedBy(currentUser);
			wt.setAssignedTo(contact);
			workTaskDao.persist(wt);
		}
	}

	@Transactional
	public List<WorkTask> loadWorkTasksRaised(Person currentUser) {
		return workTaskDao.loadAllWorkTasksRaisedByUser(currentUser);
	}

	@Transactional
	public List<WorkTask> loadWorkTasksAssignedTo(Person currentUser) {
		return workTaskDao.loadAllWorkTasksRaisedToUser(currentUser);
	}

	@Transactional
	public void updateWorkTask(String state, String body, String userName, long assignedToId, String priority, long taskId) {
		WorkTask wt = loadWorkTask(taskId);
		wt.setState(State.valueOf(state));
		wt.setPriority(Priorities.valueOf(priority));
		if (wt.getAssignedTo().getId() != assignedToId) {
			Contactable ass = contactService.loadContactable(assignedToId);
			wt.setAssignedTo(ass);
		}

		if (body != null && !"".equals(body.trim())) {
			Account acc = accountService.loadAccountByUserName(userName);
			Person currentUser = acc.getUserProfile();
			TicketUpdate update = new TicketUpdate();
			update.setDetails(body);
			update.setTicket(wt);
			update.setRaisedBy(currentUser);
			update.setAssignedTo(wt.getRaisedBy());
			wt.addUpdate(update);
			entityManager.persist(update);
		}
	}

	@Transactional
	public List<WorkTask> getLatestWorkTasks(int limit) {
		return workTaskDao.loadLatestWorktTasks(limit);
	}
}