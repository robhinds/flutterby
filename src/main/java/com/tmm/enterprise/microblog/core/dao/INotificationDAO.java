package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Notification;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.enums.ObjectType;

public interface INotificationDAO {
	List<Notification> loadNotificationsByUser(Contactable person, int page);

	List<Notification> loadUnreadNotificationsByUser(Contactable person, ObjectType... type);

	Notification findNotification(Person user, long activityId);
}
