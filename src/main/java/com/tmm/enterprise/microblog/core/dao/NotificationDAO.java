package com.tmm.enterprise.microblog.core.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Notification;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.enums.ObjectType;

@SuppressWarnings("unchecked")
@Repository(value = "notificationDAO")
public class NotificationDAO extends GenericHibernateDAO<Notification, Long> implements INotificationDAO {

	@Override
	public List<Notification> loadNotificationsByUser(Contactable person, int page) {
		if (person.getNotifications().isEmpty()) {
			return new ArrayList<Notification>();
		}
		Query query = getEntityManager().createQuery("select n from Notification n where n IN (:nots) order by n.activity.creationDate desc");
		query.setParameter("nots", person.getNotifications());
		query.setFirstResult(page * 20);
		query.setMaxResults((page * 20) + 20);
		List<Notification> s = (List<Notification>) query.getResultList();
		return s;
	}

	@Override
	public List<Notification> loadUnreadNotificationsByUser(Contactable person, ObjectType... type) {
		if (person.getNotifications().isEmpty()) {
			return new ArrayList<Notification>();
		}
		Query query = getEntityManager().createQuery(
				"select n from Notification n where n IN (:nots) "
						+ "and n.read=false and "+buildTypeQueryString(type)+" order by n.activity.creationDate asc");
		query.setParameter("nots", person.getNotifications());
		int i=0; 
		for(ObjectType t : type)
		{
			query.setParameter("type"+i, t.toString());
			i++;
		}
		List<Notification> s = (List<Notification>) query.getResultList();
		return s;
	}

	private String buildTypeQueryString(ObjectType...type)
	{
		StringBuilder builder = new StringBuilder();
		String[] result = new String[type.length];
		for(int i =0; i<type.length;i++)
		{
			result[i] = "n.discriminator=:type"+i; 
		}
		builder.append(StringUtils.join(result," or "));
		return builder.toString();
	}


	@Override
	public Notification findNotification(Person user, long activityId) {
		if (user.getNotifications().isEmpty()) {
			return null;
		}
		Query query = getEntityManager().createQuery(
				"select n from Notification n where n IN (:nots) and n.activity.id=:activityId order by n.activity.creationDate asc");
		query.setParameter("nots", user.getNotifications());
		query.setParameter("activityId", activityId);

		List<Notification> s = (List<Notification>) query.getResultList();
		if (s == null || s.size() == 0) {
			return null;
		} else {
			return s.get(0);
		}
	}

}