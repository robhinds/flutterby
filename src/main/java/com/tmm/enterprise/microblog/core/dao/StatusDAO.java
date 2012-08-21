/**
 * 
 */
package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Status;


/**
 * @author robert.hinds
 *
 */
@Repository(value="statusDAO")
public class StatusDAO extends GenericHibernateDAO<Status, Long> implements IStatusDAO{
	
	/* (non-Javadoc)
	 * @see com.tmm.enterprise.microblog.core.dao.IStatusDAO#loadAllStatus(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Status> loadAllStatus(int limit)
	{
		Query query = getEntityManager().createQuery("select s from Status s order by s.creationDate desc");
		query.setMaxResults(limit);
		List<Status> s = (List<Status>) query.getResultList();
		return s;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tmm.enterprise.microblog.core.dao.IStatusDAO#loadAllStatusByUser(com.tmm.enterprise.microblog.domain.Contactable)
	 */
	@SuppressWarnings("unchecked")
	public List<Status> loadAllStatusByUser(Contactable user)
	{
		Query query = getEntityManager().createQuery("select s from Status s where s.raisedBy = ?1");
		query.setParameter(1, user);
		List<Status> s = (List<Status>) query.getResultList();
		return s;
	}


}
