/**
 * 
 */
package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Team;
import com.tmm.enterprise.microblog.domain.WorkTask;

/**
 * @author robert.hinds
 * 
 */
@Repository(value = "workTaskDAO")
public class WorkTaskDAO extends GenericHibernateDAO<WorkTask, Long> implements IWorkTaskDAO {

	@SuppressWarnings("unchecked")
	public List<WorkTask> loadAllWorkTasksRaisedByUser(Contactable user) {
		Query query = getEntityManager().createQuery("select wt from WorkTask wt where wt.raisedBy = ?1");
		query.setParameter(1, user);
		List<WorkTask> wts = (List<WorkTask>) query.getResultList();
		return wts;
	}

	@SuppressWarnings("unchecked")
	public List<WorkTask> loadAllWorkTasksRaisedToUser(Contactable user) {
		if (user instanceof Person && ((Person) user).getTeam() != null) {
			Team team = ((Person) user).getTeam();
			Query query = getEntityManager().createQuery("select w from WorkTask w where w.assignedTo = ?1 or w.assignedTo = ?2");
			query.setParameter(1, user);
			query.setParameter(2, team);
			List<WorkTask> tasks = (List<WorkTask>) query.getResultList();
			return tasks;
		} else {
			Query query = getEntityManager().createQuery("select w from WorkTask w where w.assignedTo = ?1");
			query.setParameter(1, user);
			List<WorkTask> tasks = (List<WorkTask>) query.getResultList();
			return tasks;
		}
	}

	@SuppressWarnings("unchecked")
	public List<WorkTask> loadLatestWorktTasks(int limit) {
		Query query = getEntityManager().createQuery("select wt from WorkTask wt order by wt.creationDate asc");
		query.setFirstResult(0);
		query.setMaxResults(limit);
		List<WorkTask> wt = (List<WorkTask>) query.getResultList();
		return wt;
	}

}
