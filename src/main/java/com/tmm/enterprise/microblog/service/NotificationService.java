package com.tmm.enterprise.microblog.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.enterprise.microblog.core.dao.INotificationDAO;
import com.tmm.enterprise.microblog.domain.Notification;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.enums.ObjectType;

@Service("notificationService")
@Repository
@Transactional
public class NotificationService {

	private EntityManager entityManager;
	@Autowired
	private INotificationDAO notificationDao;

	@Transactional
	public void createNotification(Notification notification) {
		entityManager.persist(notification);
	}

	@Transactional
	public void deleteNotification(Notification notification) {
		entityManager.remove(notification);
	}

	public List<Notification> loadNotificationsForUser(Person person, int page) {
		return notificationDao.loadNotificationsByUser(person, page);
	}

	public List<Notification> loadUnreadNotificationsForUser(Person person, ObjectType... type) {
		return notificationDao.loadUnreadNotificationsByUser(person, type);
	}


	@PersistenceContext
	public void setEntityManager(EntityManager manager) {
		this.entityManager = manager;
	}

	@Transactional
	public void markAsRead(Person user, long activityId) {
		Notification n = notificationDao.findNotification(user, activityId);
		if (n != null) {
			n.setRead(true);
		}
	}

}
