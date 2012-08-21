package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tmm.enterprise.microblog.domain.Activity;
import com.tmm.enterprise.microblog.domain.Contactable;

/**
 * @author robert.hinds
 * 
 */
@Repository(value = "activityDAO")
public class ActivityDAO extends GenericHibernateDAO<Activity, Long> implements IActivityDAO {

	@SuppressWarnings("unchecked")
	public List<Activity> loadAllPublicActivitiesByUser(Contactable user, int page) {
		Query query = getEntityManager().createQuery(
				"select act from Activity act where act.raisedBy = ?1 and act.class!='TODO' order by act.creationDate desc");
		query.setParameter(1, user);
		query.setFirstResult(page * 20);
		query.setMaxResults((page * 20) + 20);
		List<Activity> s = (List<Activity>) query.getResultList();
		return s;
	}

}