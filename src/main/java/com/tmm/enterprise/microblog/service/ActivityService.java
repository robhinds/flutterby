package com.tmm.enterprise.microblog.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.enterprise.microblog.core.dao.IActivityDAO;
import com.tmm.enterprise.microblog.domain.Activity;
import com.tmm.enterprise.microblog.domain.Contactable;

@Service("activityService")
@Repository
@Transactional
public class ActivityService {
	private EntityManager entityManager;

	@Autowired
	private IActivityDAO activityDao;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public List<Activity> loadLatestPublicStatus(Contactable currentUser, int pageNum) {
		return activityDao.loadAllPublicActivitiesByUser(currentUser, pageNum);
	}

}