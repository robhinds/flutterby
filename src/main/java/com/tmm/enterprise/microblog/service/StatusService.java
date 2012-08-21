package com.tmm.enterprise.microblog.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.enterprise.microblog.core.dao.StatusDAO;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Status;
import com.tmm.enterprise.microblog.security.Account;

@Service("statusService")
@Repository
@Transactional
public class StatusService {
	private EntityManager entityManager;

	@Autowired
	private AccountService accountService;

	@Autowired
	private StatusDAO statusDao;

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

	@Transactional
	public void createStatus(Status s) {
		statusDao.persist(s);
	}

	public Status loadStatus(long id) {
		return getEntityManager().find(Status.class, id);
	}

	public List<Status> loadTenLatestStatuses() {
		return statusDao.loadAllStatus(10);
	}

	public List<Status> loadLatestStatuses(int limit) {
		return statusDao.loadAllStatus(limit);
	}

	@Transactional
	public void createStatus(String newStatus, String userName) {
		Account acc = accountService.loadAccountByUserName(userName);
		Person currentUser = acc.getUserProfile();
		if (currentUser != null) {
			Status s = new Status(currentUser, newStatus);
			currentUser.addStatus(s);
			statusDao.persist(s);
		}
	}

}